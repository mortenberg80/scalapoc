package no.iterate.scalapoc.dao

import scala.slick.driver.JdbcProfile
import com.github.tototoshi.slick.GenericJodaSupport

trait DbProfile {
  val profile: JdbcProfile
  val jodaSupport: GenericJodaSupport
}
