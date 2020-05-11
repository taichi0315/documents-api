package json.reads.document

import play.api.libs.json._

import lib.model.{User, Document}

case class JsValueReadsDocument(
  url: String,
)

object JsValueReadsDocument {
  implicit val documentReads = Json.reads[JsValueReadsDocument]

  def toWithNoId(document: JsValueReadsDocument, uid: User.Id): Document.WithNoId = {
    Document(
      document.url,
      uid
    )
  }  
}
