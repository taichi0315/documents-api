package lib.model

import ixias.model._
import ixias.security.PBKDF2

import java.time.LocalDateTime

// ユーザーを表すモデル
//~~~~~~~~~~~~~~~~~~~~
import UserAuth._
case class UserAuth(
  id:        Option[User.Id],
  email:     String,
  hash:      String,
  updatedAt: LocalDateTime = NOW,
  createdAt: LocalDateTime = NOW
) extends EntityModel[User.Id]

// コンパニオンオブジェクト
//~~~~~~~~~~~~~~~~~~~~~~~~
object UserAuth {

  type WithNoId = Entity.WithNoId [User.Id, UserAuth]
  type EmbeddedId = Entity.EmbeddedId[User.Id, UserAuth]

  // INSERT時のIDがAutoincrementのため,IDなしであることを示すオブジェクトに変換
  def apply(id: Option[User.Id], email: String, password: String): EmbeddedId = {
    new Entity.EmbeddedId(
      new UserAuth(
        id       = id,
        email    = email,
        hash     = hash(password)
      )
    )
  }

  def hash(password: String): String = PBKDF2.hash(password)

  def verify(password: String, hash: String): Boolean = PBKDF2.compare(password, hash)
}
