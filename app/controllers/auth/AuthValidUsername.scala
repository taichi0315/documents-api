package controllers.auth

import javax.inject.{Singleton, Inject}

import scala.concurrent.{ExecutionContext, Future}
import play.api.mvc._
import play.api.libs.json._
import ixias.play.api.mvc.BaseExtensionMethods

import json.reads.auth.JsValueReadsAuthValidUsername
import json.writes.auth.JsValueWritesAuthValid

import lib.persistence.default.UserRepository

@Singleton
class AuthValidUsernameController @Inject() (
  val controllerComponents: ControllerComponents
) (implicit val ec: ExecutionContext)
extends BaseController with BaseExtensionMethods {
  
  def post() = Action.async { implicit request =>
    
    val jsReadsAuth = JsonHelper.bindFromRequest[JsValueReadsAuthValidUsername]

    jsReadsAuth match {
      case Left(error) => Future.successful(error)
      case Right(auth) =>
        for {
          userOpt <- UserRepository.getByUsername(auth.username)
        } yield {
          userOpt match {
            case Some(user) => Ok(Json.toJson(JsValueWritesAuthValid.toWrites(false)))
            case None       => Ok(Json.toJson(JsValueWritesAuthValid.toWrites(true)))
          }
        }
    }
  }
}
