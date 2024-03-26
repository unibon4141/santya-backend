package domains.usecases

import domains.repositories.ShopSearchRepository
import entities.{ImageFile, Shop, ShopId}

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ShopSearchUsecaseInteractor @Inject() (
    shopSearchRepository: ShopSearchRepository,
)(implicit
    ex: ExecutionContext
) extends ShopSearchUsecaseInputPort {
  override def handle(input: ShopSearchInputData): Future[(Seq[(Shop)], Seq[(Int, String)] )]= {
    for{
      //ユースケースに定義しているinputケースクラスを使っていいのか、あとで老人確認する
      shops <- shopSearchRepository.fetch(input)
      images <- shopSearchRepository.fetchImage(shops.map(_.id))
    } yield (shops, images)

  }
}
