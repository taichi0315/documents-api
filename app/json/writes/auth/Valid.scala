package json.writes.auth

import java.time.LocalDate
import play.api.libs.json._

import lib.model.{User, AuthToken}

case class JsValueWritesAuthValid(
  isValid: Boolean
)

object JsValueWritesAuthValid {

  implicit val authWrites = Json.writes[JsValueWritesAuthValid]

  def toWrites(isValid: Boolean): JsValueWritesAuthValid = {
    JsValueWritesAuthValid(
      isValid = isValid
    )
  }
}
