package controllers.document

import javax.inject.{Singleton, Inject}

import scala.concurrent.ExecutionContext
import play.api.mvc._
import play.api.libs.json._

import mvc.action.AuthenticationAction

import json.writes.document.JsValueWritesDocument

import lib.model.{User, Document}
import lib.persistence.default.{UserRepository, DocumentRepository}

@Singleton
class DocumentListController @Inject() (
  val controllerComponents: ControllerComponents
) (implicit val ec: ExecutionContext)
extends BaseController {
  
  def list() = (Action andThen AuthenticationAction()).async { implicit request =>

    val uid: User.Id = request.authToken.v.uid

    for {
      documentSeq <- DocumentRepository.list
      Some(user)  <- UserRepository.get(uid)
    } yield {
      val jsDocumentSeq = documentSeq.map(document =>
        JsValueWritesDocument.toWrites(document, user)
      )

      Ok(Json.toJson(jsDocumentSeq))
    }
  }
}
