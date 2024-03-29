package domains.usecases

import entities.{ImageFile, ShopId}

import scala.concurrent.Future

trait ShopImageUsecaseInputPort {
  def handle(input: ShopImageInputData): Future[Int]

  def handleBinary(input: ShopImageBinaryInputData): Future[Int]
}

case class ShopImageInputData(paths: Seq[ImageFile], shopId: ShopId)
case class ShopImageBinaryInputData(content: Array[Byte], shopId: ShopId)
