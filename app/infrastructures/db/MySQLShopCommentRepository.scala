package infrastructures.db

import entities.{CommentId, ShopComment, ShopId, UserId}
import slickSchema.{Tables => T}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.MySQLProfile.api._
import slick.jdbc.JdbcProfile

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}
import domains.repositories.ShopCommentRepository
import domains.usecases.ShopPostCommentInputData
class MySQLShopCommentRepository @Inject() (
                                             protected val dbConfigProvider: DatabaseConfigProvider
                                           )(implicit ec: ExecutionContext)
  extends ShopCommentRepository
    with HasDatabaseConfigProvider[JdbcProfile] {
  def fetch(shopId: ShopId):Future[Seq[ShopComment]] = {
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

  def postComment(input: ShopPostCommentInputData): Future[Boolean] = {
    val action = T.Comment.map(c => (c.shopId, c.userId, c.title, c.sentence)) += (input.shopId.value, input.userId.value, input.title, input.sentence)
    db.run(action).map{ result =>
      if(result > 0) {
//        投稿に成功した場合
        true
      } else {
//        失敗した場合
        false
      }
    }
  }
}
