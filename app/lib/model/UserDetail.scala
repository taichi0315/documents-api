package lib.model

import ixias.model._

import java.time.LocalDateTime

import lib.model.User

// ユーザーを表すモデル
//~~~~~~~~~~~~~~~~~~~~
import UserDetail._
case class UserDetail(
  id:        Option[User.Id],
  nickname:  String,
  updatedAt: LocalDateTime = NOW,
  createdAt: LocalDateTime = NOW
) extends EntityModel[User.Id]

// コンパニオンオブジェクト
//~~~~~~~~~~~~~~~~~~~~~~~~
object UserDetail {

  type WithNoId = Entity.WithNoId    [User.Id, UserDetail]
  type EmbeddedId = Entity.EmbeddedId[User.Id, UserDetail]

  // INSERT時のIDがAutoincrementのため,IDなしであることを示すオブジェクトに変換
  def apply(id: Option[User.Id], nickname: String): EmbeddedId = {
    new Entity.EmbeddedId(
      new UserDetail(
        id       = id,
        nickname = nickname,
      )
    )
  }
}
