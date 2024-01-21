package domains.usecases

import domains.repositories.ShopDetailRepository
import entities.Shop

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ShopDetailUsecaseInteractor @Inject() (
    shopDetailRepository: ShopDetailRepository
)(implicit
    ex: ExecutionContext
) extends ShopDetailUsecaseInputPort {
  def handle(input: ShopDetailInputData): Future[Shop] = {
    shopDetailRepository.fetch(input.id)
  }
}
