package controllers.auth

import javax.inject.{Singleton, Inject}

import scala.concurrent.{Future, ExecutionContext}
import play.api.mvc._
import play.api.libs.json._
import ixias.play.api.mvc.BaseExtensionMethods

import mvc.action.AuthenticationAction

import lib.model.AuthToken
import lib.persistence.default.{UserRepository, UserAuthRepository, AuthTokenRepository}

@Singleton
class AuthSignOutController @Inject() (
  val controllerComponents: ControllerComponents
) (implicit val ec: ExecutionContext)
extends BaseController with BaseExtensionMethods {
  
  def delete() = (Action andThen AuthenticationAction()).async { implicit request =>
    
    val id: AuthToken.Id = request.authToken.id

    for {
      _ <- AuthTokenRepository.remove(id)
    } yield NoContent
  }
}
