package controllers.endpoints

import controllers.responses.ShopDetailResponse
import javax.inject._
import scala.concurrent.ExecutionContext
import domains.usecases.{ShopDetailInputData, ShopDetailUsecaseInputPort}
import entities.ShopId
import io.circe.syntax._
import io.circe.generic.auto._
import play.api.libs.circe.Circe
import play.api.mvc._

//店舗の詳細情報を取得するコントローラー
@Singleton
class ShopDetailController @Inject() (
    ShopDeailhUsecase: ShopDetailUsecaseInputPort,
    val controllerComponents: ControllerComponents
)(implicit ec: ExecutionContext)
    extends BaseController
    with Circe {
  def index(id: Int): Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent] =>
      val input = ShopDetailInputData(ShopId(id))
      for {
        output <- ShopDeailhUsecase.handle(input)
      } yield
         Ok(ShopDetailResponse.make(output).asJson)
  }
}
