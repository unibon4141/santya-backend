package controllers.endpoints

import controllers.requests.ShopCommentRequest
import controllers.responses.ShopCommentResponse

import javax.inject._
import scala.concurrent.ExecutionContext
import domains.usecases.{ShopCommentUsecaseInputPort, ShopPostCommentInputData}
import entities.{ShopId, UserId}
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
  /**
   * 店舗の口コミ一覧を取得する
   * @param shopId
   * @return 店舗の口コミ一覧
   */
  def index(shopId: Int): Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent] =>
      val input = ShopId(shopId)
      for {
        output <- ShopCommentUsecase.handle(input)
      } yield
        Ok(ShopCommentResponse.make(output).asJson)
  }

  def post(shopId: Int) = Action(circe.json[ShopCommentRequest]).async {
    implicit  request =>
      val input = ShopPostCommentInputData(
        shopId = ShopId(request.body.shop_id),
        userId = UserId(request.body.user_id),
        title = request.body.title,
        sentence = request.body.sentence
      )
      for {
        output <- ShopCommentUsecase.post(input)
      } yield {
        if(output) {
          Status(200)
        } else {
          Status(409)
        }
      }
  }
}
