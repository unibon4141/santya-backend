//package controllers.responses
//
//import entities.{PriceRangeId, Shop, ShopId}
//import java.time.{LocalDate, LocalDateTime}
//
//final case class ShopDetailResponse(
//    shop_id: Int,
//    shop_name: String,
//    map_id: Int,
//    genre_id: Int,
//    genre_name: String,
//    scene_ids: Seq[Int],
//    scene_names: Seq[String],
//    lunch_price_range_id: Option[Int],
//    dinner_price_range_id: Option[Int],
//    lunch_min_price: Option[Int],
//    lunch_max_price: Option[Int],
//    dinner_min_price: Option[Int],
//    dinner_max_price: Option[Int],
//    shop_address: String,
//    distance: Double,
//    created_time: Option[LocalDateTime],
//    updated_time: Option[LocalDateTime],
//
//)
//object ShopDetailResponse {
//  def make(shop: Shop) = {
//    ShopDetailResponse(
//      shop_id = shop.id.value,
//      shop_name = shop.name,
//      map_id = shop.mapId.value,
//      genre_id = shop.genreId.value,
//      scene_id1 =
//        if (shop.sceneIds.length > 0) Option(shop.sceneIds(0).value) else None,
//      scene_id2 = Option(1),
//      lunch_price_range_id = shop.lunchPriceRangeId.value,
//      dinner_price_range_id = shop.dinnerPriceRangeId.value,
//      shop_address = shop.shopAddress.value,
//      distance = shop.distance,
//      lunch_min_price = None,
//      lunch_max_price = None,
//      dinner_min_price = None,
//      dinner_max_price = None,
//      created_time = None,
//      updated_time = None
//    )
//  }
//}
