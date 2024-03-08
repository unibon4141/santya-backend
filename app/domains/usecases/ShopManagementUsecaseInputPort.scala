package domains.usecases

import controllers.requests.ShopManagementRequest
import entities.{GenreId, PriceRangeId, SceneId, Shop}

import scala.concurrent.Future

trait ShopManagementUsecaseInputPort {
  def handle(input: ShopManagementInputData): Future[Boolean]
}

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

