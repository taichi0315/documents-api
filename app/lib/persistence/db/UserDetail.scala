package lib.persistence.db

import java.time.LocalDateTime
import slick.jdbc.JdbcProfile
import ixias.persistence.model.Table

import lib.model.{User, UserDetail}

// UserTable: Userテーブルへのマッピングを行う
//~~~~~~~~~~~~~~
case class UserDetailTable[P <: JdbcProfile]()(implicit val driver: P)
  extends Table[UserDetail, P] {
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
  class Table(tag: Tag) extends BasicTable(tag, "user_details") {
    import UserDetail._
    // Columns
    /* @1 */ def id        = column[User.Id]       ("user_id",    O.UInt64, O.PrimaryKey)
    /* @2 */ def nickname  = column[String]        ("nickname",   O.Utf8Char255)
    /* @3 */ def updatedAt = column[LocalDateTime] ("updated_at", O.TsCurrent)
    /* @4 */ def createdAt = column[LocalDateTime] ("created_at", O.Ts)

    type TableElementTuple = (
      Option[User.Id], String, LocalDateTime, LocalDateTime
    )

    // DB <=> Scala の相互のmapping定義
    def * = (id.?, nickname, updatedAt, createdAt) <> (
      // Tuple(table) => Model
      (t: TableElementTuple) => UserDetail(
        t._1, t._2, t._3, t._4
      ),
      // Model => Tuple(table)
      (v: TableElementType) => UserDetail.unapply(v).map { t => (
        t._1, t._2, LocalDateTime.now(), t._4
      )}
    )
  }
}
