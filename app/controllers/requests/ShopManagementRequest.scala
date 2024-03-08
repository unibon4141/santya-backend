package controllers.requests

case class ShopManagementRequest(
                                shop_name: String,
                                genre_id: Int,
                                scene_id1: Int,
                                scene_id2: Int,
                                lunch_price_range_id: Int,
                                dinner_price_range_id: Int,
                                shop_address: String,
                                distance: Double
                                )
