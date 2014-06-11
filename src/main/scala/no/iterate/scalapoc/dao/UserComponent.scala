package no.iterate.scalapoc.dao

trait UserComponent { this: DbProfile =>

  import no.iterate.scalapoc.domain.User
  import profile.simple._

  class Users(tag: Tag) extends Table[User](tag, "users") {
    def id = column[Int]("user_id", O.PrimaryKey)
    def email = column[String]("email_address")
    def firstName = column[String]("first_name")
    def lastName = column[String]("last_name")

    def * = (id, email, firstName, lastName) <> (User.tupled, User.unapply)
  }

  val Users = TableQuery[Users]
}