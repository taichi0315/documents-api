package lib.infrastructure

import scala.concurrent.{ExecutionContext, Future}

import net.ruippeixotog.scalascraper.browser.JsoupBrowser

class DocumentBrowser()(implicit ec: ExecutionContext)

object DocumentBrowser {

  lazy val browser = JsoupBrowser()
  
  def getTitle(url: String): Future[Option[String]] = {
    val title = browser.get(url).title
    title.isEmpty match {
      case true  => Future.successful(None)
      case false => Future.successful(Some(title))
    }
  }
}
