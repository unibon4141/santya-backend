package domains.usecases

import domains.repositories.ShopSearchRepository
import entities.{Shop, ShopId}

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ShopSearchUsecaseInteractor @Inject() (
    shopSearchRepository: ShopSearchRepository,
)(implicit
    ex: ExecutionContext
) extends ShopSearchUsecaseInputPort {
  def handle(): Future[Seq[Shop]] = {
    for{
      shops <- shopSearchRepository.fetch()
    } yield shops

  }
}
