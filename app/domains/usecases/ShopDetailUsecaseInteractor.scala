package domains.usecases

import domains.repositories.{ShopDetailRepository, ShopSearchRepository}
import entities.Shop

import java.util.Base64
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
    } yield {
      val imagesFiltered =  images.filter(_._2.isDefined).map(a => (a._1, a._2.get))
      val output = imagesFiltered.map { image =>
        //        バイナリ文字列に変換
        ( image._1,  "data:image/jpg;base64,"+Base64.getEncoder().encodeToString(image._2))

      }
      (shop, output)
    }
  }
}
