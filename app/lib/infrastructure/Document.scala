package lib.infrastructure

import scala.concurrent.{ExecutionContext, Future}

import net.ruippeixotog.scalascraper.browser.JsoupBrowser

class DocumentBrowser()(implicit ec: ExecutionContext)

object DocumentBrowser {

  val browser = JsoupBrowser()
  
  def getTitle(url: String): Future[String] = {
    val doc = browser.get(url)
    Future.successful(doc.title)
  }
}
