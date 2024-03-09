package domains.usecases

import controllers.requests.ShopManagementRequest
import entities.{GenreId, PriceRangeId, SceneId, Shop, ShopId}

import scala.concurrent.Future

trait ShopManagementUsecaseInputPort {
  def handle(input: ShopManagementInputData): Future[Boolean]

  //  TODO : もう少し良い（わかりやすい）名前ない？
  def edit(input: ShopManagementEditInputData): Future[Boolean]
}

//店舗追加用
final case class ShopManagementInputData(
                                          shopName: String,
                                          genreId: GenreId,
                                          sceneId1: SceneId,
                                          sceneId2: SceneId,
                                          lunchPriceRangeId: PriceRangeId,
                                          dinnerPriceRangeId: PriceRangeId,
                                          shop_address: String,
                                          distance: Double
                                        )

//店舗編集用
final case class ShopManagementEditInputData(
                                              shopId: ShopId,
                                              shopName: String,
                                              genreId: GenreId,
                                              sceneId1: SceneId,
                                              sceneId2: SceneId,
                                              lunchPriceRangeId: PriceRangeId,
                                              dinnerPriceRangeId: PriceRangeId,
                                              shop_address: String,
                                              distance: Double
                                            )

