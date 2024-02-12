package domains.repositories

import entities.ShopId

trait ShopCommentRepository {
  def fetch(shopId: ShopId)
}
