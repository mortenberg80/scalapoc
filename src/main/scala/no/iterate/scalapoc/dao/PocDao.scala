package no.iterate.scalapoc.dao

import com.github.tototoshi.slick.GenericJodaSupport
import scala.slick.driver.JdbcProfile

class PocDao(override val profile: JdbcProfile, override val jodaSupport: GenericJodaSupport)
  extends UserComponent with DbProfile {

  import no.iterate.scalapoc.domain._
  import profile.simple._
  import jodaSupport._
  import org.joda.time.LocalDate

  def initDb(implicit s: Session) {
    (Users.ddl).create
  }

  def allUsers(implicit s: Session): List[User] = Users.sortBy(_.id).list()

  def user(id: Int)(implicit s: Session): Option[User] = Users.filter(_.id === id).firstOption

}
