package json.reads.auth

import java.util.UUID
import play.api.libs.json._

import lib.model.{User, UserAuth, AuthToken}

case class JsValueReadsAuthSignUp(
  username: String,
  email:    String,
  password: String
)

object JsValueReadsAuthSignUp {
  implicit val authReads = Json.reads[JsValueReadsAuthSignUp]

  def toUser(auth: JsValueReadsAuthSignUp): User.WithNoId = {
    User(
      username = auth.username
    )
  }

  def toUserAuth(id: User.Id, auth: JsValueReadsAuthSignUp): UserAuth.EmbeddedId = {
    UserAuth(
      id       = Some(id),
      email    = auth.email,
      password = auth.password
    )
  }

  def toAuthToken(uid: User.Id): AuthToken.WithNoId = {
    AuthToken(
      uid = uid,
      token = UUID.randomUUID.toString
    )
  }
}
