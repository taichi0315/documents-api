package json.writes.user

import java.time.LocalDate
import play.api.libs.json._

import lib.model.User.EmbeddedId

case class JsValueWritesUser(
  username: String,
)

object JsValueWritesUser {

  implicit val userWrites = Json.writes[JsValueWritesUser]

  def toWrites(user: EmbeddedId): JsValueWritesUser = {
    JsValueWritesUser(
      username = user.v.username
    )
  }
}
