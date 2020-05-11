package lib.model

import ixias.model._

import java.time.LocalDateTime

// ユーザーを表すモデル
//~~~~~~~~~~~~~~~~~~~~
import Document._
case class Document(
  id:        Option[Id],
  url:       String,
  uid:       User.Id,
  updatedAt: LocalDateTime = NOW,
  createdAt: LocalDateTime = NOW
) extends EntityModel[Id]

// コンパニオンオブジェクト
//~~~~~~~~~~~~~~~~~~~~~~~~
object Document {

  val  Id = the[Identity[Id]]
  type Id = Long @@ Document
  type WithNoId = Entity.WithNoId [Id, Document]
  type EmbeddedId = Entity.EmbeddedId[Id, Document]

  // INSERT時のIDがAutoincrementのため,IDなしであることを示すオブジェクトに変換
  def apply(url: String, uid: User.Id): WithNoId = {
    new Entity.WithNoId(
      new Document(
        id     = None,
        url    = url,
        uid    = uid
      )
    )
  }
}
