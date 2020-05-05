package lib.persistence

import scala.concurrent.Future
import ixias.persistence.SlickRepository
import lib.model.{User, UserDetail}
import slick.jdbc.JdbcProfile

// UserRepository: UserTableへのクエリ発行を行うRepository層の定義
//~~~~~~~~~~~~~~~~~~~~~~
case class UserDetailRepository[P <: JdbcProfile]()(implicit val driver: P)
  extends SlickRepository[User.Id, UserDetail, P]
  with db.SlickResourceProvider[P] {

  import api._

  def list(): Future[Seq[EntityEmbeddedId]] =
    RunDBAction(UserDetailTable, "slave") {_
      .result
    }

  /**
    * Get User Data
    */
  def get(id: User.Id): Future[Option[EntityEmbeddedId]] =
    RunDBAction(UserDetailTable, "slave") { _
      .filter(_.id === id)
      .result.headOption
  }

  /**
    * Add User Data
   */
  def add(entity: EntityWithNoId): Future[User.Id] =
    RunDBAction(UserDetailTable) { slick =>
      slick returning slick.map(_.id) += entity.v
    }

  /**
   * Update User Data
   */
  def update(entity: EntityEmbeddedId): Future[Option[EntityEmbeddedId]] =
    RunDBAction(UserDetailTable) { slick =>
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
  def remove(id: User.Id): Future[Option[EntityEmbeddedId]] =
    RunDBAction(UserDetailTable) { slick =>
      val row = slick.filter(_.id === id)
      for {
        old <- row.result.headOption
        _   <- old match {
          case None    => DBIO.successful(0)
          case Some(_) => row.delete
        }
      } yield old
    }
}
