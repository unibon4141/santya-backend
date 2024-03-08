package domains.repositories

import domains.usecases.ShopPostCommentInputData
import entities.{ShopComment, ShopId}

import scala.concurrent.Future

trait ShopCommentRepository {
  def fetch(shopId: ShopId): Future[Seq[ShopComment]]

  def postComment(input: ShopPostCommentInputData): Future[Boolean]
}
