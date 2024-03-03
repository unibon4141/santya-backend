package domains.repositories

import entities.{ShopComment, ShopId}

import scala.concurrent.Future

trait ShopCommentRepository {
  def fetch(shopId: ShopId): Future[Seq[ShopComment]]
}
