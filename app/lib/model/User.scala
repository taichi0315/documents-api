package lib.model

import ixias.model._

import java.time.LocalDateTime

// ユーザーを表すモデル
//~~~~~~~~~~~~~~~~~~~~
import User._
case class User(
  id:        Option[Id],
  username:  String,
  updatedAt: LocalDateTime = NOW,
  createdAt: LocalDateTime = NOW
) extends EntityModel[Id]

// コンパニオンオブジェクト
//~~~~~~~~~~~~~~~~~~~~~~~~
object User {

  val  Id = the[Identity[Id]]
  type Id = Long @@ User
  type WithNoId = Entity.WithNoId [Id, User]
  type EmbeddedId = Entity.EmbeddedId[Id, User]

  // INSERT時のIDがAutoincrementのため,IDなしであることを示すオブジェクトに変換
  def apply(username: String): WithNoId = {
    new Entity.WithNoId(
      new User(
        id       = None,
        username = username,
      )
    )
  }
}
