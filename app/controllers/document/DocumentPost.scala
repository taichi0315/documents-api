package controllers.document

import javax.inject.Inject

import play.api.mvc._
import play.api.libs.json._
import scala.concurrent.{Future, ExecutionContext}

import ixias.play.api.mvc.BaseExtensionMethods

import mvc.action.AuthenticationAction

import json.reads.document.JsValueReadsDocument

import lib.model.User
import lib.persistence.default.{UserRepository, DocumentRepository}

class DocumentPostController @Inject()(
  val controllerComponents: ControllerComponents,
) (implicit val ec: ExecutionContext) 
extends BaseController with BaseExtensionMethods{

  def post = (Action andThen AuthenticationAction()).async { implicit request =>
    
    val uid: User.Id = request.authToken.v.uid
    val jsDocument   = JsonHelper.bindFromRequest[JsValueReadsDocument]
    
    jsDocument match {
      case Left(error) => Future.successful(error)
      case Right(document) => {
        for {
          _ <- DocumentRepository.add(JsValueReadsDocument.toWithNoId(document, uid))
        } yield NoContent
      }
    }
  }
}
