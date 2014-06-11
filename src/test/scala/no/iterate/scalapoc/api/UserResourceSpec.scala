package no.iterate.scalapoc.api

import no.iterate.scalapoc.dao.TestDb
import no.iterate.scalapoc.UnitSpec
import no.iterate.scalapoc.domain.User
import org.joda.time.LocalDate

class UserResourceSpec extends UnitSpec with TestDb {

  "Querying for all users" should "return users in the database" in {
    testInTransaction(dbScript = "users.sql", f = { implicit session =>
      val users = dao.allUsers

      users.size shouldBe 2

      val per = users.find(_.firstName == "Per").get

      per.lastName shouldBe "Hansen"
    })
  }
}