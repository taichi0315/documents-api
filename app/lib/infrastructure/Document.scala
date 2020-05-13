package lib.infrastructure

import scala.util.Try
import scala.concurrent.{ExecutionContext, Future}

import net.ruippeixotog.scalascraper.browser.JsoupBrowser

class DocumentBrowser

object DocumentBrowser {

  implicit def executionContext = ExecutionContext.Implicits.global

  lazy val browser = JsoupBrowser()

  def getDocument(url: String): Future[browser.DocumentType] =
    Future.fromTry(
      Try(browser.get(url))
    )
  
  def getTitle(url: String): Future[Option[String]] =
    for {
      document <- getDocument(url)
    } yield {
      val title = document.title
      
      title.isEmpty match {
        case true  => None
        case false => Some(title)
      }
    }
}
