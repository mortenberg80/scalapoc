package no.iterate.scalapoc

import org.json4s._
import org.json4s.ext._
import org.json4s.native.Serialization.write
import org.joda.time.LocalDate
import scala.slick.jdbc.JdbcBackend.{Database, Session}
import scala.util.Try
import unfiltered.directives._
import unfiltered.directives.Directives._
import unfiltered.request._
import unfiltered.response._
import no.iterate.scalapoc.dao.PocDao

class UserResource(dao: PocDao, db: Database) extends unfiltered.filter.Plan {

  implicit val formats = DefaultFormats ++ JodaTimeSerializers.all

  def json(obj: AnyRef) = JsonContent ~> ResponseString(write(obj))
  def notFound(msg: String) = NotFound ~> ResponseString(msg)
  def badParam(msg: String) = BadRequest ~> ResponseString(msg)

  val asLocalDate = data.Fallible[String, LocalDate](s => Try { LocalDate parse s }.toOption)

  implicit def required[T] = data.Requiring[T].fail(name => badParam(s"$name is missing"))
  implicit def implyDate = data.as.String ~> asLocalDate.fail((k, v) => badParam(s"'$v' is not a valid date"))

  object IntId {
    def unapply(idStr: String) = Try { idStr.toInt }.toOption
  }

  /** Provides an implicit session for the request-response cycle. */
  def SlickCycle[A,B](intent: Session => unfiltered.Cycle.Intent[A,B]): unfiltered.Cycle.Intent[A,B] = {
    case req =>
      db.withSession { implicit session =>
        intent(session).lift(req).getOrElse(Pass)
      }
  }

  def intent = SlickCycle { implicit session =>
    Directive.Intent.Path {
      case "/users" =>
        for {
          _ <- GET
        } yield json(dao.allUsers)

      case Seg(List("users", IntId(id))) =>
        for {
          _ <- GET
          user <- getOrElse(dao.user(id), notFound("User not found"))
        } yield json(user)
    }
  }
}
