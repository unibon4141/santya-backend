package domains.repositories

import domains.usecases.{ShopDetailInputData, ShopSearchInputData}
import entities.Shop

import scala.concurrent.Future
trait ShopSearchRepository {
  def fetchByWord(input: ShopSearchInputData): Future[Seq[Shop]]
}
