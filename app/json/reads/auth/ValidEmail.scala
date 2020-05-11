package json.reads.auth

import play.api.libs.json._

case class JsValueReadsAuthValidEmail(
  email: String
)

object JsValueReadsAuthValidEmail {
  implicit val authReads = Json.reads[JsValueReadsAuthValidEmail]
}
