package controllers.endpoints

import controllers.requests.{ShopManagementEditRequest, ShopManagementRequest}

import javax.inject._
import scala.concurrent.ExecutionContext
import domains.usecases.{ShopManagementEditInputData, ShopManagementInputData, ShopManagementUsecaseInputPort}
import entities.{GenreId, PriceRangeId, SceneId, ShopId}
import io.circe.syntax._
import io.circe.generic.auto._
import play.api.libs.circe.Circe
import play.api.mvc._

//店舗の追加丶編集ができるコントローラー
@Singleton
class ShopManagementController @Inject()(
                                          ShopManagementUsecase: ShopManagementUsecaseInputPort,
                                          val controllerComponents: ControllerComponents,
                                          auth: Auth
                                        )(implicit ec: ExecutionContext)
  extends BaseController
    with Circe {
  def index() = {
    auth(circe.json[ShopManagementRequest]).async {
          implicit request =>
            val input = ShopManagementInputData(
              shopName = request.body.shop_name,
              genreId = GenreId(request.body.genre_id),
              sceneId1 = SceneId(request.body.scene_id1),
              sceneId2 = SceneId(request.body.scene_id2),
              lunchPriceRangeId = PriceRangeId(request.body.lunch_price_range_id),
              dinnerPriceRangeId = PriceRangeId(request.body.dinner_price_range_id),
              shop_address = request.body.shop_address,
              distance = request.body.distance
            )
            ShopManagementUsecase.handle(input).map { result =>
              //店舗の追加に成功した場合
              if (result) {
                Status(200)
              } else {
                //店舗名の重複があった場合は、エラーを返す
                Status(409)
              }
            }
        }

    }

  def edit(shopId: Int) =
    auth(circe.json[ShopManagementEditRequest]).async {
    implicit request =>
      val input = ShopManagementEditInputData(
        shopId = ShopId(request.body.shop_id),
        shopName = request.body.shop_name,
        genreId = GenreId(request.body.genre_id),
        sceneId1 = SceneId(request.body.scene_id1),
        sceneId2 = SceneId(request.body.scene_id2),
        lunchPriceRangeId = PriceRangeId(request.body.lunch_price_range_id),
        dinnerPriceRangeId = PriceRangeId(request.body.dinner_price_range_id),
        shop_address = request.body.shop_address,
        distance = request.body.distance
      )
      println("ok")
      ShopManagementUsecase.edit(input).map{ result =>
        //店舗の編集に成功した場合
        if(result) {
          Status(200)
        } else {
          //店舗名の重複があった場合は、エラーを返す
          Status(409)
        }
      }
  }
}