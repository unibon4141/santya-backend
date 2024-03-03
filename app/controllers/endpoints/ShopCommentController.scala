package controllers.endpoints

import controllers.responses.{ShopCommentResponse, ShopDetailResponse}

import javax.inject._
import scala.concurrent.ExecutionContext
import domains.usecases.{ShopCommentInputData, ShopCommentUsecaseInputPort, ShopDetailInputData, ShopDetailUsecaseInputPort}
import entities.ShopId
import io.circe.syntax._
import io.circe.generic.auto._
import play.api.libs.circe.Circe
import play.api.mvc._

//店舗の全コメントを取得するコントローラー
@Singleton
class ShopCommentController @Inject() (
                                       ShopCommentUsecase: ShopCommentUsecaseInputPort,
                                       val controllerComponents: ControllerComponents
                                     )(implicit ec: ExecutionContext)
  extends BaseController
    with Circe {
  def index(shopId: Int): Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent] =>
//      val input = ShopCommentInputData(ShopId(shopId))
      val input = ShopId(shopId)
      for {
        output <- ShopCommentUsecase.handle(input)
      } yield
        Ok(ShopCommentResponse.make(output).asJson)
  }
}
