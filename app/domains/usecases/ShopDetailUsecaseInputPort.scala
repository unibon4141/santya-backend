package domains.usecases

import entities.{Shop, ShopId}
import scala.concurrent.Future

trait ShopDetailUsecaseInputPort {
  def handle(input: ShopDetailInputData): Future[Shop]
}

case class ShopDetailInputData(id: ShopId)
