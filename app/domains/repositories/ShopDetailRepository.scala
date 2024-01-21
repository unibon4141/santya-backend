package domains.repositories

import entities.{Shop, ShopId}
import scala.concurrent.Future

trait ShopDetailRepository {
  def fetch(id: ShopId): Future[Shop]
}
