package domains.usecases

import entities.{Shop, ShopId}

import scala.concurrent.Future

trait ShopDetailUsecaseInputPort {
  def handle(input: ShopDetailInputData): Future[(Shop, Seq[(Int, String)])]
}

case class ShopDetailInputData(id: ShopId)
