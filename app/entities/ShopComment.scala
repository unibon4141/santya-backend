package entities

case class ShopComment(
                      commentId: CommentId,
                      shopId: ShopId,
                      userId: UserId,
                      title: String,
                      sentence: String
                      )
