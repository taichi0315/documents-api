package lib.persistence.db

import java.time.LocalDateTime
import slick.jdbc.JdbcProfile
import ixias.persistence.model.Table

import lib.model.{User, Document}

// UserTable: Userテーブルへのマッピングを行う
//~~~~~~~~~~~~~~
case class DocumentTable[P <: JdbcProfile]()(implicit val driver: P)
  extends Table[Document, P] {
  import api._

  // Definition of DataSourceName
  //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  lazy val dsn = Map(
    "master" -> DataSourceName("ixias.db.mysql://master/documents_api"),
    "slave"  -> DataSourceName("ixias.db.mysql://slave/documents_api")
  )

  // Definition of Query
  //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  class Query extends BasicQuery(new Table(_)) {}
  lazy val query = new Query

  // Definition of Table
  //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  class Table(tag: Tag) extends BasicTable(tag, "documents") {
    import Document._
    // Columns
    /* @1 */ def id        = column[Id]             ("id",         O.UInt64, O.PrimaryKey, O.AutoInc)
    /* @2 */ def url       = column[String]         ("url",        O.Utf8Char255)
    /* @3 */ def uid       = column[User.Id]        ("user_id",    O.UInt64)
    /* @4 */ def title     = column[Option[String]] ("title",      O.Utf8Char255)
    /* @5 */ def updatedAt = column[LocalDateTime]  ("updated_at", O.TsCurrent)
    /* @6 */ def createdAt = column[LocalDateTime]  ("created_at", O.Ts)

    type TableElementTuple = (
      Option[Id], String, User.Id, Option[String], LocalDateTime, LocalDateTime
    )

    // DB <=> Scala の相互のmapping定義
    def * = (id.?, url, uid, title, updatedAt, createdAt) <> (
      // Tuple(table) => Model
      (t: TableElementTuple) => Document(
        t._1, t._2, t._3, t._4, t._5, t._6
      ),
      // Model => Tuple(table)
      (v: TableElementType) => Document.unapply(v).map { t => (
        t._1, t._2, t._3, t._4, LocalDateTime.now(), t._6
      )}
    )
  }
}
