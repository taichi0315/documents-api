package lib.model

import ixias.model._

import java.time.LocalDateTime
import scala.concurrent.duration.Duration

// ユーザーを表すモデル
//~~~~~~~~~~~~~~~~~~~~
import AuthToken._
case class AuthToken(
  id:        Option[Id],
  uid:       User.Id,
  token:     String,
  expiry:    LocalDateTime,
  updatedAt: LocalDateTime = NOW,
  createdAt: LocalDateTime = NOW
) extends EntityModel[Id]

// コンパニオンオブジェクト
//~~~~~~~~~~~~~~~~~~~~~~~~
object AuthToken {

  val  Id = the[Identity[Id]]
  type Id = Long @@ AuthToken
  type WithNoId = Entity.WithNoId    [Id, AuthToken]
  type EmbeddedId = Entity.EmbeddedId[Id, AuthToken]

  // INSERT時のIDがAutoincrementのため,IDなしであることを示すオブジェクトに変換
  def apply(uid: User.Id, token: String): WithNoId = {
    new Entity.WithNoId(
      new AuthToken(
        id     = None,
        uid    = uid,
        token  = token,
        expiry = LocalDateTime.now().plusHours(12)
      )
    )
  }
}
