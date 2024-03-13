package controllers.endpoints

import javax.inject._
import scala.concurrent.{ExecutionContext, Future}
import play.api.mvc._
import java.time.Clock
import pdi.jwt.{Jwt, JwtAlgorithm, JwtClaim, JwtHeader, JwtOptions}
import play.api.Configuration
import play.api.mvc.Results. Status
import scala.util.{Failure, Success}

class Auth @Inject()(
                                config: Configuration,
                                parser: BodyParsers.Default
                              )(implicit ec: ExecutionContext)
  extends ActionBuilderImpl(parser)
 {
  override def invokeBlock[A](request: Request[A], block: Request[A] => Future[Result]): Future[Result] = {
    implicit val clock: Clock = Clock.systemUTC
    val token = request.headers.get("Authorization")
  token match {
      case Some(t) =>
        //Authorizationヘッダからtokenを読み込むj
        val tokenDecoded = Jwt.decodeRaw(t, config.get[String]("enval.secret_key"), Seq(JwtAlgorithm.HS256))
        tokenDecoded match {
          case Success(r) =>  block(request)
          case Failure(exception) => Future(Status(401))
        }
      case _ =>
//        認証が失敗した場合（jwtの改ざんがあった場合など）
        Future(Status(401))
    }
  }
}