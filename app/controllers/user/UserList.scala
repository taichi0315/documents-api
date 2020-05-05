package controllers.user

import javax.inject.{Singleton, Inject}

import scala.concurrent.ExecutionContext
import play.api.mvc._
import play.api.libs.json._

import mvc.action.AuthenticationAction

import json.writes.JsValueWritesUser

import lib.model.User
import lib.persistence.default.UserRepository

@Singleton
class UserListController @Inject() (
  val controllerComponents: ControllerComponents
) (implicit val ec: ExecutionContext)
extends BaseController {
  
  def findAll() = (Action andThen AuthenticationAction()).async { implicit request =>
    for {
      userSeq <- UserRepository.list
    } yield {
      val jsUserSeq = userSeq.map(user =>
        JsValueWritesUser.toWrites(user)
      )

      Ok(Json.toJson(jsUserSeq))
    }
  }

}
