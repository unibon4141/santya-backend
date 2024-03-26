package domains.usecases

import domains.repositories.{ShopDetailRepository, ShopSearchRepository}
import entities.Shop

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ShopDetailUsecaseInteractor @Inject() (
    shopDetailRepository: ShopDetailRepository,
    shopSearchRepository: ShopSearchRepository
)(implicit
    ex: ExecutionContext
) extends ShopDetailUsecaseInputPort {
  def handle(input: ShopDetailInputData): Future[(Shop, Seq[(Int, String)])] = {
    shopDetailRepository.fetch(input.id)

    for {
      shop <- shopDetailRepository.fetch(input.id)
      images <- shopSearchRepository.fetchImage(Seq(shop.id))
    } yield (shop, images)
  }
}
