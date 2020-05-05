package mvc.action

import play.api.mvc._
import play.api.mvc.Results._

import scala.concurrent.{ExecutionContext, Future}

import lib.model.AuthToken
import lib.persistence.default.AuthTokenRepository

case class UserRequest[A](
  authToken: AuthToken.EmbeddedId,
  request:   Request[A]
) extends WrappedRequest[A](request)

case class AuthenticationAction()(implicit
  val executionContext: ExecutionContext
) extends ActionRefiner[Request, UserRequest] {

  protected def refine[A](request: Request[A]): Future[Either[Result, UserRequest[A]]] = {
    val hTokenOpt = request.headers.get("Authorization")
    val next      = hTokenOpt match {
      case None         => Future.successful(Left(Unauthorized))
      case Some(hToken) =>
        for {
          authTokenOpt <- AuthTokenRepository.getByToken(hToken) 
        } yield {
          authTokenOpt match {
            case None            => Left(Unauthorized)
            case Some(authToken) => Right(UserRequest(authToken, request))
          }
        }
    }
    next
  }
}
