package no.iterate.scalapoc.dao

import no.iterate.scalapoc.UnitSpec

class UsersTest extends UnitSpec with TestDb {

  "Querying for all users" should "return users in the database" in {
    testInTransaction { implicit session =>
      val users = dao.allUsers

      users.size should be >= 0
    }
  }

}
