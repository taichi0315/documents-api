package json.writes.auth

import java.time.LocalDate
import play.api.libs.json._

import lib.model.{User, AuthToken}

case class JsValueWritesAuthSign(
  username: String,
  token:    String
)

object JsValueWritesAuthSign {

  implicit val authWrites = Json.writes[JsValueWritesAuthSign]

  def toWrites(user: User.EmbeddedId, authToken: AuthToken.EmbeddedId): JsValueWritesAuthSign = {
    JsValueWritesAuthSign(
      username = user.v.username,
      token    = authToken.v.token
    )
  }
}
