package controllers.document

import javax.inject.{Singleton, Inject}

import play.api.mvc._
import play.api.libs.json._
import scala.concurrent.{Future, ExecutionContext}

import mvc.action.AuthenticationAction

import lib.model.Document
import lib.persistence.default.DocumentRepository

class DocumentDeleteController @Inject()(
  val controllerComponents: ControllerComponents,
) (implicit val ec: ExecutionContext) 
extends BaseController {

  def delete(id: Long) = (Action andThen AuthenticationAction()).async { implicit request =>
    for {
      documentOpt <- DocumentRepository.remove(Document.Id(id))
    } yield {
      documentOpt match {
        case Some(document) => NoContent
        case None           => NotFound
      }
    }
  }
}
