package lib.persistence.db

import slick.jdbc.JdbcProfile

// Tableを扱うResourceのProvider
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
trait SlickResourceProvider[P <: JdbcProfile] {

  implicit val driver: P
  object UserTable       extends UserTable
  object UserAuthTable   extends UserAuthTable
  object UserDetailTable extends UserDetailTable
  object AuthTokenTable  extends AuthTokenTable
  object DocumentTable   extends DocumentTable
  // --[ テーブル定義 ] --------------------------------------
  lazy val AllTables = Seq(
    UserTable,
    UserAuthTable,
    UserDetailTable,
    AuthTokenTable,
    DocumentTable
  )
}
