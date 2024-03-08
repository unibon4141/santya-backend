package controllers.requests

case class ShopCommentRequest(
                               shop_id: Int,
                               user_id: Int,
                               title: String,
                               sentence: String
                             )

