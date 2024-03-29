package domains.repositories

import domains.usecases.ShopImageInputData
import entities.{ImageFile, ShopId}

import scala.concurrent.Future

trait ShopImageRepository {
  def fetch(paths: Seq[ImageFile], shopId: ShopId ): Future[Int]

  def saveImage(shopId: ShopId, image: Array[Byte]): Future[Int]
}
