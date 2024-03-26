package domains.repositories

import domains.usecases.{ShopDetailInputData, ShopSearchInputData}
import entities.{Shop, ShopId}

import scala.concurrent.Future
trait ShopSearchRepository {
  def fetch(input: ShopSearchInputData): Future[Seq[Shop]]

  def fetchImage(shopIds: Seq[ShopId]):Future[Seq[(Int, String)]]
}