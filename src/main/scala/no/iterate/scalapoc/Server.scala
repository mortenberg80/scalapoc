package no.iterate.scalapoc

import com.github.tototoshi.slick.PostgresJodaSupport
import org.slf4j.LoggerFactory
import scala.slick.driver.PostgresDriver
import scala.slick.jdbc.JdbcBackend.Database
import unfiltered.jetty.Http
import com.mchange.v2.c3p0.ComboPooledDataSource
import no.iterate.scalapoc.dao.PocDao

object Server {

  private val logger = LoggerFactory getLogger "no.iterate.scalapoc.Server"

  def main(args: Array[String]) {
    val port = scala.util.Try { args(0).toInt }.getOrElse(8080)

    val datasource = createDatasource
    val db = Database forDataSource datasource

    val dao = new PocDao(PostgresDriver, PostgresJodaSupport)

    def afterStart(svr: Http#ServerBuilder) = {}
    def afterStop(svr: Http#ServerBuilder) = datasource.close()

    unfiltered.jetty.Http.apply(port, "0.0.0.0")
      .filter(new UserResource(dao, db))
      .run(afterStart, afterStop)
  }

  private def createDatasource = {
    import com.mchange.v2.c3p0.ComboPooledDataSource

    val dbHost = Option(System getenv "DB_HOST") getOrElse "127.0.0.1"
    val jdbcUrl = s"jdbc:postgresql://$dbHost/scalapoc"
    logger.info(s"Setting up Data source with URL '$jdbcUrl'")

    val datasource = new ComboPooledDataSource
    datasource setDriverClass "org.postgresql.Driver"
    datasource setJdbcUrl jdbcUrl
    Option(System getenv "DB_USER") foreach { user => datasource setUser user }
    Option(System getenv "DB_PASS") foreach { password => datasource setPassword password }
    datasource
  }

}

