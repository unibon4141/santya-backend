package controllers.endpoints

import controllers.requests.{UserLoginRequest, UserSignUpRequest}
import controllers.responses.{UserLoginResponse, UserSignUpResponse}

import javax.inject._
import scala.concurrent.ExecutionContext
import domains.usecases.{UserLoginData, UserSignUpData, UserUsecaseInputPort}
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
      // ログイン処理
      for {
        result <- userUsecase.login(input)
      } yield {
        //正しいユーザーだった場合
        if(result.value > 0) {
          val token = Jwt.encode(JwtClaim({s"""{"user_id":${result.value}}"""})
            //有効期限を３日に設定
            .issuedNow.expiresIn(86400*3), config.get[String]("enval.secret_key"),
            JwtAlgorithm.HS256)
          //レスポンスとしてtokenをJSONに変換して返す
          Ok(UserLoginResponse(Option(token)).asJson)
        } else {
          Ok(UserLoginResponse(None).asJson)
        }
      }
  }

  def signUp() = Action(circe.json[UserSignUpRequest]).async {
    implicit request =>
      implicit val clock: Clock = Clock.systemUTC
      val username = request.body.username
      val password = request.body.password
      val input = UserSignUpData(username, password)
      // ユーザー登録処理
      for {
        result <- userUsecase.signUp(input)
      } yield {
        //正常に登録できた場合
        if(result > 0) {
//          TODO loginと処理を共通化させる
          val token = Jwt.encode(JwtClaim({s"""{"user_id":${result}}"""})
            //有効期限を３日に設定
            .issuedNow.expiresIn(86400*3), config.get[String]("enval.secret_key"),
            JwtAlgorithm.HS256)
          //レスポンスとしてtokenをJSONに変換して返す
          Ok(UserSignUpResponse(Option(token)).asJson)
        } else {
          Ok(UserSignUpResponse(None).asJson)
        }
      }
  }

}