package lib.persistence.db

import java.time.LocalDateTime
import slick.jdbc.JdbcProfile
import ixias.persistence.model.Table

import lib.model.{User, UserAuth}

// UserTable: Userテーブルへのマッピングを行う
//~~~~~~~~~~~~~~
case class UserAuthTable[P <: JdbcProfile]()(implicit val driver: P)
  extends Table[UserAuth, P] {
  import api._

  // Definition of DataSourceName
  //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  lazy val dsn = Map(
    "master" -> DataSourceName("ixias.db.mysql://master/auth_api"),
    "slave"  -> DataSourceName("ixias.db.mysql://slave/auth_api")
  )

  // Definition of Query
  //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  class Query extends BasicQuery(new Table(_)) {}
  lazy val query = new Query

  // Definition of Table
  //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  class Table(tag: Tag) extends BasicTable(tag, "user_auths") {
    import UserAuth._
    // Columns
    /* @1 */ def id        = column[User.Id]       ("user_id",    O.UInt64, O.PrimaryKey)
    /* @2 */ def email     = column[String]        ("email",      O.Utf8Char255, O.Unique)
    /* @3 */ def hash      = column[String]        ("hash",       O.Utf8Char255)
    /* @4 */ def updatedAt = column[LocalDateTime] ("updated_at", O.TsCurrent)
    /* @5 */ def createdAt = column[LocalDateTime] ("created_at", O.Ts)

    type TableElementTuple = (
      Option[User.Id], String, String, LocalDateTime, LocalDateTime
    )

    // DB <=> Scala の相互のmapping定義
    def * = (id.?, email, hash, updatedAt, createdAt) <> (
      // Tuple(table) => Model
      (t: TableElementTuple) => UserAuth(
        t._1, t._2, t._3, t._4, t._5
      ),
      // Model => Tuple(table)
      (v: TableElementType) => UserAuth.unapply(v).map { t => (
        t._1, t._2, t._3, LocalDateTime.now(), t._5
      )}
    )
  }
}
