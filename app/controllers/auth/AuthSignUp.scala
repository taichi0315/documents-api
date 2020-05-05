package controllers.auth

import javax.inject.{Singleton, Inject}

import scala.concurrent.{Future, ExecutionContext}
import play.api.mvc._
import play.api.libs.json._
import ixias.play.api.mvc.BaseExtensionMethods

import json.reads.JsValueReadsAuth
import json.writes.JsValueWritesAuth

import lib.model.{User, UserAuth}
import lib.persistence.default.{UserRepository, UserAuthRepository, AuthTokenRepository}

@Singleton
class AuthSignUpController @Inject() (
  val controllerComponents: ControllerComponents
) (implicit val ec: ExecutionContext)
extends BaseController with BaseExtensionMethods {
  
  def post() = Action.async { implicit request =>
    
    val jsAuth = JsonHelper.bindFromRequest[JsValueReadsAuth]

    jsAuth match {
      case Left(error) => Future.successful(error)
      case Right(auth) => {
        for {
          uid             <- UserRepository.add(JsValueReadsAuth.toUser(auth))
          _               <- UserAuthRepository.addEmbeddedId(JsValueReadsAuth.toUserAuth(uid, auth))
          aid             <- AuthTokenRepository.add(JsValueReadsAuth.toAuthToken(uid))
          Some(user)      <- UserRepository.get(uid)
          Some(authToken) <- AuthTokenRepository.get(aid)
        } yield {
          Ok(Json.toJson(JsValueWritesAuth.toWrites(user, authToken)))
        }
      }
    }
  }
}
