package controllers.endpoints

import controllers.responses.ShopResponse
import play.api.mvc._

import javax.inject._
import scala.concurrent.ExecutionContext
import domains.usecases.{ShopSearchInputData, ShopSearchUsecaseInputPort}
import io.circe.syntax._
import io.circe.generic.auto._
import play.api.libs.circe.Circe

//店舗一覧の表示用コントローラーroutes
//TODO ワード検索、シーン検索ができるように改修する
@Singleton
class ShopSearchController @Inject() (
                                       ShopSearchUsecase: ShopSearchUsecaseInputPort,
                                       val controllerComponents: ControllerComponents
                                     )(implicit ec: ExecutionContext)
  extends BaseController
    with Circe {

  //  全店舗を取得する
  def index(word: Option[String], sceneId: Option[Int]): Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent] =>
      val input = ShopSearchInputData(word, sceneId)
      for {
        output <- ShopSearchUsecase.handle(input)
      } yield
        Ok(ShopResponse.make(output).asJson)
  }

}
