package json.writes

import java.time.LocalDate
import play.api.libs.json._

import lib.model.{User, AuthToken}

case class JsValueWritesAuth(
  username: String,
  token:    String
)

object JsValueWritesAuth {

  implicit val authWrites = Json.writes[JsValueWritesAuth]

  def toWrites(user: User.EmbeddedId, authToken: AuthToken.EmbeddedId): JsValueWritesAuth = {
    JsValueWritesAuth(
      username = user.v.username,
      token    = authToken.v.token
    )
  }
}
