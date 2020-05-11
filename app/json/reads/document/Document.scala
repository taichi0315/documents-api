package json.reads.document

import play.api.libs.json._

case class JsValueReadsDocument(
  url:      String,
  username: String
)

object JsValueReadsDocument {
  implicit val documentReads = Json.reads[JsValueReadsDocument]
}
