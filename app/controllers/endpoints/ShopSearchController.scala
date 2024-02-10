package controllers.endpoints

import controllers.responses.{ ShopResponse}
import play.api.mvc._

import javax.inject._
import scala.concurrent.ExecutionContext
import domains.usecases.{ShopSearchInputData, ShopSearchUsecaseInputPort}
import entities.ShopId
import io.circe.syntax._
import io.circe.generic.auto._
import play.api.libs.circe.Circe

//店舗一覧の表示用コントローラー
//TODO ワード検索、シーン検索ができるように改修する
@Singleton
class ShopSearchController @Inject() (
                                       ShopSearchUsecase: ShopSearchUsecaseInputPort,
                                       val controllerComponents: ControllerComponents
                                     )(implicit ec: ExecutionContext)
  extends BaseController
    with Circe {

  //  全店舗を取得する
  def index(): Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent] =>
      for {
        output <- ShopSearchUsecase.handle()
      } yield
        Ok(ShopResponse.make(output).asJson)
  }

}
