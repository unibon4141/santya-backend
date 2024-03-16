package controllers.endpoints

import controllers.requests.{UserLoginRequest, UserSignUpRequest}
import controllers.responses.{ShopResponse, ToggleFavoriteResponse, UserIdAuthResponse, UserLoginResponse, UserSignUpResponse}

import javax.inject._
import scala.concurrent.{ExecutionContext, Future}
import domains.usecases.{UserLoginData, UserSignUpData, UserUsecaseInputPort, getFavoriteShopsData, toggleFavoriteShopData}
import entities.{ShopId, UserId}
import io.circe.{Decoder, HCursor, KeyDecoder, KeyEncoder}
import io.circe.syntax._
import io.circe.generic.auto._
import play.api.libs.circe.Circe
import play.api.mvc._

import java.time.Clock
import pdi.jwt.{Jwt, JwtAlgorithm, JwtCirce, JwtClaim, JwtHeader, JwtOptions}
import play.api.Configuration

import scala.util.{Failure, Success, Try}

//店舗の追加丶編集ができるコントローラー
//@Singleton
class UserController @Inject()(
                                          userUsecase: UserUsecaseInputPort,
                                          val controllerComponents: ControllerComponents,
                                          auth: Auth,
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

  /**
   * リクエストしたユーザーのIDを取得
   * @return userId
   */
  def getUserIdBytoken() = Action{
    implicit request =>
      implicit val clock: Clock = Clock.systemUTC
      val token = request.headers.get("Authorization")
      token match {
        case Some(t) =>
          //Authorizationヘッダからtokpenを読み込むj
          val tokenDecoded = Jwt.decode(t, config.get[String]("enval.secret_key"), Seq(JwtAlgorithm.HS256))
          tokenDecoded match {
            case Success(value) =>
              val tokenStr = value.content
              val pattern = "user_id"
              //claimからuserIdを取得
              val start = pattern.r.findAllIn(tokenStr).matchData.map(_.end).toList.head+2
              val end = "}".r.findAllIn(tokenStr).matchData.map(_.start).toList.head
              val output = UserIdAuthResponse(tokenStr.substring(start,end).toInt).asJson
              Ok(output)

            case Failure(exception) => Status(401)
          }
        case _ =>
          //認証が失敗した場合（jwtの改ざんがあった場合など）
        Status(401)
      }
  }

  /**
   * お気に入り店舗の一覧を取得
   * @return
   */
  def getFavoriteShops(userId: Int) =
    auth.async {
      implicit request =>
        implicit val clock: Clock = Clock.systemUTC
        val token = request.headers.get("Authorization")
        token match {
          case Some(t) =>
            //Authorizationヘッダからtokpenを読み込むj
            val tokenDecoded = Jwt.decode(t, config.get[String]("enval.secret_key"), Seq(JwtAlgorithm.HS256))
            tokenDecoded match {
              case Success(value) =>
                val tokenStr = value.content
                val pattern = "user_id"
                //claimからuserIdを取得
                val start = pattern.r.findAllIn(tokenStr).matchData.map(_.end).toList.head + 2
                val end = "}".r.findAllIn(tokenStr).matchData.map(_.start).toList.head
                val userIdByToken = tokenStr.substring(start, end).toInt
//                本人確認
                if(userIdByToken == userId) {
//                  お気に入り店舗取得
                  val input  = getFavoriteShopsData(UserId(userId))
                  for {
                    shops <- userUsecase.getFavoriteShops(input)
                  } yield {
                    Ok(ShopResponse.make(shops).asJson)
                  }

                } else {
                  Future.successful(Status(401))
                }
              case Failure(exception) => Future.successful(Status(401))
            }
          case _ =>
            //認証が失敗した場合（jwtの改ざんがあった場合など）
            Future.successful(Status(401))
        }
    }

  /**
   * お気に入り店舗の追加・削除
   * @param userId
   * @return
   */
  def toggleFavoriteShop(userId: Int, shopId: Int) =
    auth.async {
      implicit request =>
        implicit val clock: Clock = Clock.systemUTC
        val token = request.headers.get("Authorization")
        token match {
          case Some(t) =>
            //Authorizationヘッダからtokpenを読み込むj
            val tokenDecoded = Jwt.decode(t, config.get[String]("enval.secret_key"), Seq(JwtAlgorithm.HS256))
            tokenDecoded match {
              case Success(value) =>
                val tokenStr = value.content
                val pattern = "user_id"
                //claimからuserIdを取得
                val start = pattern.r.findAllIn(tokenStr).matchData.map(_.end).toList.head + 2
                val end = "}".r.findAllIn(tokenStr).matchData.map(_.start).toList.head
                val userIdByToken = tokenStr.substring(start, end).toInt
                //本人確認
                if(userIdByToken == userId) {
                  val input  = toggleFavoriteShopData(UserId(userId), ShopId(shopId))
                  for {
                    result <- userUsecase.toggleFavoriteShop(input)
                  } yield {
                    Ok(ToggleFavoriteResponse(result).asJson)
                  }

                } else {
                  Future.successful(Status(401))
                }
              case Failure(exception) => Future.successful(Status(401))
            }
          case _ =>
            //認証が失敗した場合（jwtの改ざんがあった場合など）
            Future.successful(Status(401))
        }
    }

//  private def getUserIdyString(claimOpt: Option[String], userId: Int): Future[Result] = {
//    implicit val clock: Clock = Clock.systemUTC
//    claimOpt match {
//      case Some(claim) =>
//        //Authorizationヘッダからtokpenを読み込むj
//        val tokenDecoded = Jwt.decode(claim, config.get[String]("enval.secret_key"), Seq(JwtAlgorithm.HS256))
//        tokenDecoded match {
//          case Success(value) =>
//            val tokenStr = value.content
//            val pattern = "user_id"
//            //claimからuserIdを取得
//            val start = pattern.r.findAllIn(tokenStr).matchData.map(_.end).toList.head + 2
//            val end = "}".r.findAllIn(tokenStr).matchData.map(_.start).toList.head
//            val userIdByToken = tokenStr.substring(start, end).toInt
//            //本人確認
//            if (userIdByToken == userId) {
//              val input = toggleFavoriteShopData(UserId(userId), ShopId(shopId))
//              for {
//                result <- userUsecase.toggleFavoriteShop(input)
//              } yield {
//                Ok(ToggleFavoriteResponse(result).asJson)
//              }
//
//            } else {
//              Future.successful(Status(401))
//            }
//          case Failure(exception) => Future.successful(Status(401))
//        }
//      case _ =>
//        //認証が失敗した場合（jwtの改ざんがあった場合など）
//        Future.successful(Status(401))
//    }
//  }

}