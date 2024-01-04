package domains.repositories

import entities.Shop

import scala.concurrent.Future
trait ShopSearchRepository {
  def fetch(): Future[Seq[Shop]]
}
