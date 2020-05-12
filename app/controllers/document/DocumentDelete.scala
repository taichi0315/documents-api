package controllers.document

import javax.inject.{Singleton, Inject}

import play.api.mvc._
import play.api.libs.json._
import scala.concurrent.{Future, ExecutionContext}

import lib.model.Document
import lib.persistence.default.DocumentRepository

class DocumentDeleteController @Inject()(
  val controllerComponents: ControllerComponents,
) (implicit val ec: ExecutionContext) 
extends BaseController {

  def delete(id: Long) = Action.async { implicit request =>
    for {
      Some(document) <- DocumentRepository.get(Document.Id(id))
      _              <- DocumentRepository.remove(document.id)
    } yield NoContent
  }
}
