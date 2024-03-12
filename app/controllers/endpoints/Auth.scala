package controllers.endpoints



import akka.http.scaladsl.model.HttpHeader.ParsingResult.Ok
import controllers.requests.UserLoginRequest
import controllers.responses.UserLoginResponse

import javax.inject._
import scala.concurrent.{ExecutionContext, Future}
import domains.usecases.{UserLoginData, UserUsecaseInputPort}
import io.circe.syntax._
import io.circe.generic.auto._
import play.api.libs.circe.Circe
import play.api.mvc.{Action, _}

import java.time.Clock
import pdi.jwt.{Jwt, JwtAlgorithm, JwtClaim, JwtHeader, JwtOptions}
import play.api.Configuration
import play.api.mvc.Results.Redirect

//店舗の追加丶編集ができるコントローラー
//@Singleton
class Auth @Inject()(
                                userUsecase: UserUsecaseInputPort,
                                config: Configuration,
                                parser: BodyParsers.Default
                              )(implicit ec: ExecutionContext)
  extends ActionBuilderImpl(parser)
 {
  override def invokeBlock[A](request: Request[A], block: Request[A] => Future[Result]): Future[Result] = {
    implicit val clock: Clock = Clock.systemUTC
    println("invoke exe")
    val token = request.headers.get("Authorization")
    println(token)
    println("aa")
    val result: String = token match {
      case Some(t) =>
        //Authenticationヘッダからtokenを読み込むj
        val token = Jwt.decodeRaw(t, config.get[String]("enval.secret_key"), Seq(JwtAlgorithm.HS256))
        token.getOrElse("false")
      case _ => "false"

    }
    if(result == "false") {
      Future(Redirect("/shops/"))
    } else {
      block(request)

    }
  }

  //  def auth() :Action[AnyContent] = Action{
  //    implicit  request: Request[AnyContent] =>
  //      implicit val clock: Clock = Clock.systemUTC
  //      val token = request.headers.get("Authorization")
  //      println(token)
  //      println("aa")
  //      val result: String = token match {
  //        case Some(t) =>
  //          //Authenticationヘッダからtokenを読み込むj
  //          val token = Jwt.decodeRaw(t, config.get[String]("enval.secret_key"), Seq(JwtAlgorithm.HS256))
  //          token.getOrElse("false")
  //        case _ => "false"
  //
  //      }
  //      Ok(result)
  //
  //  }
}