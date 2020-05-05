package lib.persistence.db

import java.time.LocalDateTime
import slick.jdbc.JdbcProfile
import ixias.persistence.model.Table

import lib.model.{User, AuthToken}

// UserTable: Userテーブルへのマッピングを行う
//~~~~~~~~~~~~~~
case class AuthTokenTable[P <: JdbcProfile]()(implicit val driver: P)
  extends Table[AuthToken, P] {
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
  class Table(tag: Tag) extends BasicTable(tag, "auth_tokens") {
    import AuthToken._
    // Columns
    /* @1 */ def id        = column[Id]            ("id",         O.UInt64, O.PrimaryKey, O.AutoInc)
    /* @2 */ def uid       = column[User.Id]       ("user_id",    O.Utf8Char255, O.Unique)
    /* @3 */ def token     = column[String]        ("token",      O.Utf8Char255, O.Unique)
    /* @4 */ def expiry    = column[LocalDateTime] ("expiry",     O.Ts)
    /* @5 */ def updatedAt = column[LocalDateTime] ("updated_at", O.TsCurrent)
    /* @6 */ def createdAt = column[LocalDateTime] ("created_at", O.Ts)

    type TableElementTuple = (
      Option[Id], User.Id, String, LocalDateTime, LocalDateTime, LocalDateTime
    )

    // DB <=> Scala の相互のmapping定義
    def * = (id.?, uid, token, expiry, updatedAt, createdAt) <> (
      // Tuple(table) => Model
      (t: TableElementTuple) => AuthToken(
        t._1, t._2, t._3, t._4, t._5, t._6
      ),
      // Model => Tuple(table)
      (v: TableElementType) => AuthToken.unapply(v).map { t => (
        t._1, t._2, t._3, t._4, LocalDateTime.now(), t._6
      )}
    )
  }
}
