package json.reads.auth

import play.api.libs.json._

case class JsValueReadsAuthValidUsername(
  username: String
)

object JsValueReadsAuthValidUsername {
  implicit val authReads = Json.reads[JsValueReadsAuthValidUsername]
}
