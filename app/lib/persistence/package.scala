package lib

package object persistence {

  val default = onMySQL
  
  object onMySQL {
    implicit lazy val driver = slick.jdbc.MySQLProfile
    object UserRepository       extends UserRepository
    object UserAuthRepository   extends UserAuthRepository
    object UserDetailRepository extends UserDetailRepository
    object AuthTokenRepository  extends AuthTokenRepository
  }
}
