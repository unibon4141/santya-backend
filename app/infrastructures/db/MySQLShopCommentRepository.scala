package infrastructures.db

import entities.{CommentId, ShopComment, ShopId, UserId}
import slickSchema.{Tables => T}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.MySQLProfile.api._
import slick.jdbc.JdbcProfile

import javax.inject.Inject
import scala.concurrent.ExecutionContext
import domains.repositories.ShopCommentRepository
class MySQLShopCommentRepository @Inject() (
                                             protected val dbConfigProvider: DatabaseConfigProvider
                                           )(implicit ec: ExecutionContext)
  extends ShopCommentRepository
    with HasDatabaseConfigProvider[JdbcProfile] {
  def fetch(shopId: ShopId) = {
    val action = (for {
      comments <- T.Comment.filter(c => c.shopId === shopId.value)
    } yield comments).result

//    TODO これforから自分でmapに書き換えられるようになりたい
    for {
      rows <- db.run(action)
    } yield rows.map{ comment =>
      ShopComment(
        commentId = CommentId(comment.commentId),
        shopId = ShopId(comment.shopId),
        userId   = UserId(comment.userId),
        title = comment.title,
        sentence = comment.sentence
      )
    }
  }


}
