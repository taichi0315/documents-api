package controllers.document

import javax.inject.Inject

import play.api.mvc._
import play.api.libs.json._
import scala.concurrent.{Future, ExecutionContext}

import ixias.play.api.mvc.BaseExtensionMethods

import json.reads.JsValueReadsDocument
import json.writes.{JsValueWritesTodo, JsValueWritesCategory}

import libs.persistence.default.{TodoRepository, CategoryRepository}

class TodoPostController @Inject()(
  val controllerComponents: ControllerComponents,
) (implicit val ec: ExecutionContext) 
extends BaseController with BaseExtensionMethods{

  def post = Action.async { implicit req =>
    
    val jsTodo = JsonHelper.bindFromRequest[JsValueReadsTodo]
    
    jsTodo match {
      case Left(error) => Future.successful(error)
      case Right(todo) => {
        for {
          _ <- TodoRepository.add(JsValueReadsTodo.toWithNoId(todo))
        } yield NoContent
      }
    }
  }
}
