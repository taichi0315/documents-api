package json.reads

import java.util.UUID
import play.api.libs.json._

import lib.model.{User, UserAuth, AuthToken}

case class JsValueReadsAuth(
  username: Option[String],
  email:    String,
  password: String
)

object JsValueReadsAuth {
  implicit val authReads = Json.reads[JsValueReadsAuth]

  def toUser(auth: JsValueReadsAuth): User.WithNoId = {
    User(
      username = auth.username.get
    )
  }

  def toUserAuth(id: User.Id, auth: JsValueReadsAuth): UserAuth.EmbeddedId = {
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
