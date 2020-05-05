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
class AuthSignInController @Inject() (
  val controllerComponents: ControllerComponents
) (implicit val ec: ExecutionContext)
extends BaseController with BaseExtensionMethods {
  
  def post() = Action.async { implicit request =>
    
    val jsReadsAuth = JsonHelper.bindFromRequest[JsValueReadsAuth]

    jsReadsAuth match {
      case Left(error) => Future.successful(error)
      case Right(auth) => {
        for {
          Some(userAuth) <- UserAuthRepository.getByEmail(auth.email)
          result         <- UserAuth.verify(auth.password, userAuth.v.hash) match {
            case true  =>
              for {
                _               <- AuthTokenRepository.removeByUserId(userAuth.id)
                aid             <- AuthTokenRepository.add(JsValueReadsAuth.toAuthToken(userAuth.id))
                Some(user)      <- UserRepository.get(userAuth.id)
                Some(authToken) <- AuthTokenRepository.get(aid)
              } yield {
                val jsWritesAuth = JsValueWritesAuth.toWrites(user, authToken)

                Ok(Json.toJson(jsWritesAuth))
              }
            case false => Future.successful(BadRequest("password error"))
          }
        } yield result
      }
    }
  }
}
