package json.writes.document

import java.time.LocalDate
import play.api.libs.json._

import lib.model.{User, Document}

case class JsValueWritesDocument(
  id:       Long,
  url:      String,
  title:    Option[String]
)

object JsValueWritesDocument {

  implicit val documentWrites = Json.writes[JsValueWritesDocument]

  def toWrites(document: Document.EmbeddedId): JsValueWritesDocument = {
    JsValueWritesDocument(
      id       = document.id,
      url      = document.v.url,
      title    = document.v.title
    )
  }
}
