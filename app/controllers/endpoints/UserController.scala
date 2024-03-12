package controllers.endpoints

import controllers.requests.UserLoginRequest
import controllers.responses.UserLoginResponse
import javax.inject._
import scala.concurrent.ExecutionContext
import domains.usecases.{UserLoginData, UserUsecaseInputPort}
import io.circe.syntax._
import io.circe.generic.auto._
import play.api.libs.circe.Circe
import play.api.mvc.{Action, _}
import java.time.Clock
import pdi.jwt.{Jwt, JwtAlgorithm, JwtClaim, JwtHeader, JwtOptions}
import play.api.Configuration

//店舗の追加丶編集ができるコントローラー
//@Singleton
class UserController @Inject()(
                                          userUsecase: UserUsecaseInputPort,
                                          val controllerComponents: ControllerComponents,
                                          config: Configuration
                                        )(implicit ec: ExecutionContext)
  extends BaseController
    with Circe {
  def index() = Action(circe.json[UserLoginRequest]).async {
    implicit request =>
      implicit val clock: Clock = Clock.systemUTC
      val username = request.body.username
      val password = request.body.password
      val input = UserLoginData(username, password)
      // ログイン認証処理
      for {
        result <- userUsecase.login(input)
      } yield {
        //正しいユーザーだった場合
        if(result.value > 0) {
          val token = Jwt.encode(s"""{"user_id":${result.value}}""", config.get[String]("enval.secret_key") , JwtAlgorithm.HS256)
          //レスポンスとしてtokenをJSONに変換して返す
          Ok(UserLoginResponse(Option(token)).asJson)
        } else {
          Ok(UserLoginResponse(None).asJson)
        }
      }
  }

  def auth() :Action[AnyContent] = Action{
    implicit  request: Request[AnyContent] =>
      implicit val clock: Clock = Clock.systemUTC
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
      Ok(result)

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