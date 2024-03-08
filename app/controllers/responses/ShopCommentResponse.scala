package controllers.responses

import entities.ShopComment

case class ShopCommentResponse(
                              comment_id: Int,
                              shop_id: Int,
                              user_id: Int,
                              title: String,
                              sentence: String
                              )

object ShopCommentResponse {
  def make(comments: Seq[ShopComment]) = {
    def makeOne(comment: ShopComment) = {
      ShopCommentResponse(
        comment_id = comment.commentId.value,
        shop_id = comment.shopId.value,
        user_id = comment.userId.value,
        title = comment.title,
        sentence = comment.sentence
      )
    }
    comments.map{makeOne}

  }
}
