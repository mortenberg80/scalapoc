package no.iterate.scalapoc.dao

import scala.slick.jdbc.{ StaticQuery => Q }
import scala.slick.driver.H2Driver
import scala.slick.driver.H2Driver.simple._

import com.github.tototoshi.slick.H2JodaSupport

import scala.io.Source

trait TestDb {

  val dbName = "test" + getClass().getSimpleName()

  private def initDbScript(filename: String): String = {
    val sqlStream = getClass.getClassLoader().getResourceAsStream(filename)
    Source.fromInputStream(sqlStream).getLines().mkString("\n")
  }

  val dao = new PocDao(H2Driver, H2JodaSupport)

  def db = {
    val jdbcUrl: String = s"jdbc:h2:mem:$dbName;DATABASE_TO_UPPER=false"
    val database = Database.forURL(jdbcUrl, driver = "org.h2.Driver")

    database
  }

  def testInTransaction(f: Session => Unit, dbScript: String = "users.sql") {
    db.withTransaction { implicit session =>
      dao.initDb
      Q.updateNA(initDbScript(dbScript)).execute
      f(session)
    }
  }

}
