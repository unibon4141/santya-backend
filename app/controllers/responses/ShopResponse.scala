package controllers.responses

import entities.Shop

import java.time.LocalDateTime

case class ShopResponse (
                          shop_id: Int,
                          shop_name: String,
                          map_id: Option[Int],
                          genre_id: Int,
                          genre_name: String,
                          scene_ids: Seq[Int],
                          scene_names: Seq[String],
                          lunch_price_range_id: Option[Int],
                          dinner_price_range_id: Option[Int],
                          lunch_min_price: Option[Int],
                          lunch_max_price: Option[Int],
                          dinner_min_price: Option[Int],
                          dinner_max_price: Option[Int],
                          shop_address: Option[String],
                          distance: Double,
                          images: Seq[String],
                          created_time: LocalDateTime,
                          updated_time: LocalDateTime,
                        )

object ShopResponse {
  def make(shops:Seq[Shop], images: Seq[(Int, String)] ):Seq[ShopResponse] = {
    def makeOne(shop: Shop, image: Seq[String]) = {
      ShopResponse(
        shop_id = shop.id.value,
        shop_name = shop.name,
        map_id = shop.mapId.map(_.value),
        genre_id = shop.genre.id.value,
        genre_name = shop.genre.name,
        scene_ids = shop.scenes.map(_.id.value),
        scene_names = shop.scenes.map(_.name),
        lunch_price_range_id = shop.lunchPriceRange.map(_.id.value),
        dinner_price_range_id = shop.dinnerPriceRange.map(_.id.value),
        lunch_min_price = shop.lunchPriceRange.map(_.min),
        lunch_max_price = shop.lunchPriceRange.map(_.max),
        dinner_min_price = shop.dinnerPriceRange.map(_.min),
        dinner_max_price = shop.dinnerPriceRange.map(_.max),
        shop_address = shop.shopAddress.map(_.value),
        distance = shop.distance,
        images = image,
        created_time = shop.createAt,
        updated_time = shop.updatedAt
      )
    }

    shops.map {s =>
      val a = images.filter(s.id.value == _._1).map(_._2)
      makeOne(s, a)
    }
  }

  def makeNoImage(shops:Seq[Shop]):Seq[ShopResponse] = {
    def makeOne(shop: Shop) = {
      ShopResponse(
        shop_id = shop.id.value,
        shop_name = shop.name,
        map_id = shop.mapId.map(_.value),
        genre_id = shop.genre.id.value,
        genre_name = shop.genre.name,
        scene_ids = shop.scenes.map(_.id.value),
        scene_names = shop.scenes.map(_.name),
        lunch_price_range_id = shop.lunchPriceRange.map(_.id.value),
        dinner_price_range_id = shop.dinnerPriceRange.map(_.id.value),
        lunch_min_price = shop.lunchPriceRange.map(_.min),
        lunch_max_price = shop.lunchPriceRange.map(_.max),
        dinner_min_price = shop.dinnerPriceRange.map(_.min),
        dinner_max_price = shop.dinnerPriceRange.map(_.max),
        shop_address = shop.shopAddress.map(_.value),
        distance = shop.distance,
        images = Seq(),
        created_time = shop.createAt,
        updated_time = shop.updatedAt
      )
    }

    shops.map(makeOne)
  }
}
