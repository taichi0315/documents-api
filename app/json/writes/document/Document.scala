package json.writes.document

import java.time.LocalDate
import play.api.libs.json._

import lib.model.{User, Document}

case class JsValueWritesDocument(
  id:       Long,
  url:      String,
  username: String,
)

object JsValueWritesDocument {

  implicit val documentWrites = Json.writes[JsValueWritesDocument]

  def toWrites(document: Document.EmbeddedId, user: User.EmbeddedId): JsValueWritesDocument = {
    JsValueWritesDocument(
      id       = document.id,
      url      = document.v.url,
      username = user.v.username
    )
  }
}
