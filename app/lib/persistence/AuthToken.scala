package lib.persistence

import scala.concurrent.Future
import ixias.persistence.SlickRepository
import lib.model.{User, AuthToken}
import slick.jdbc.JdbcProfile

// UserRepository: UserTableへのクエリ発行を行うRepository層の定義
//~~~~~~~~~~~~~~~~~~~~~~
case class AuthTokenRepository[P <: JdbcProfile]()(implicit val driver: P)
  extends SlickRepository[AuthToken.Id, AuthToken, P]
  with db.SlickResourceProvider[P] {

  import api._

  def list(): Future[Seq[EntityEmbeddedId]] =
    RunDBAction(AuthTokenTable, "slave") {_
      .result
    }

  /**
    * Get User Data
    */
  def get(id: Id): Future[Option[EntityEmbeddedId]] =
    RunDBAction(AuthTokenTable, "slave") { _
      .filter(_.id === id)
      .result.headOption
  }

  def getByToken(token: String): Future[Option[EntityEmbeddedId]] =
    RunDBAction(AuthTokenTable, "slave") { _
      .filter(_.token === token)
      .result.headOption
  }

  /**
    * Add User Data
   */
  def add(entity: EntityWithNoId): Future[Id] =
    RunDBAction(AuthTokenTable) { slick =>
      slick returning slick.map(_.id) += entity.v
    }

  /**
   * Update User Data
   */
  def update(entity: EntityEmbeddedId): Future[Option[EntityEmbeddedId]] =
    RunDBAction(AuthTokenTable) { slick =>
      val row = slick.filter(_.id === entity.id)
      for {
        old <- row.result.headOption
        _   <- old match {
          case None    => DBIO.successful(0)
          case Some(_) => row.update(entity.v)
        }
      } yield old
    }

  /**
   * Delete User Data
   */
  def remove(id: Id): Future[Option[EntityEmbeddedId]] =
    RunDBAction(AuthTokenTable) { slick =>
      val row = slick.filter(_.id === id)
      for {
        old <- row.result.headOption
        _   <- old match {
          case None    => DBIO.successful(0)
          case Some(_) => row.delete
        }
      } yield old
    }

  def removeByUserId(uid: User.Id): Future[Option[EntityEmbeddedId]] =
    RunDBAction(AuthTokenTable) { slick =>
      val row = slick.filter(_.uid === uid)
      for {
        old <- row.result.headOption
        _   <- old match {
          case None    => DBIO.successful(0)
          case Some(_) => row.delete
        }
      } yield old
    }
}
