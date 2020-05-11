package json.reads.auth

import java.util.UUID
import play.api.libs.json._

import lib.model.{User, AuthToken}

case class JsValueReadsAuthSignIn(
  email:    String,
  password: String
)

object JsValueReadsAuthSignIn {
  implicit val authReads = Json.reads[JsValueReadsAuthSignIn]

  def toAuthToken(uid: User.Id): AuthToken.WithNoId = {
    AuthToken(
      uid = uid,
      token = UUID.randomUUID.toString
    )
  }
}
