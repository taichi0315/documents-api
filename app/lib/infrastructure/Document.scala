package lib.infrastructure

import scala.concurrent.{ExecutionContext, Future}

import net.ruippeixotog.scalascraper.browser.JsoupBrowser

class DocumentBrowser()(implicit ec: ExecutionContext)

object DocumentBrowser {
  
  def get(url: String): Future[String] = Future.successful("test")

}
