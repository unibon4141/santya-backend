package controllers.responses

import entities.{PriceRangeId, Shop, ShopId}

import java.time.{LocalDate, LocalDateTime}

final case class ShopDetailResponse(
    id: Int,
    name: String,
    mapId: Int,
    genreId: Int,
    sceneIds: Seq[Int],
    lunchPriceRangeId: Int,
    dinnerPriceRangeId: Int,
    shopAddress: String,
    distance: Double,
    lastUpdateAt: LocalDateTime
)
object ShopDetailResponse {
  def make(shop: Shop) = {
    ShopDetailResponse(
      id = shop.id.value,
      name = shop.name,
      mapId = shop.mapId.value,
      genreId = shop.genreId.value,
      sceneIds = shop.sceneIds.map(_.value),
      dinnerPriceRangeId = shop.lunchPriceRangeId.value.getOrElse(0),
      lunchPriceRangeId = shop.dinnerPriceRangeId.value.getOrElse(0),
      shopAddress = shop.shopAddress.value,
      distance = shop.distance,
      lastUpdateAt = shop.lastUpdateAt
    )
  }
}
