package domains.usecases

import domains.repositories.ShopSearchRepository
import entities.{ImageFile, Shop, ShopId}

import java.util.Base64
import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ShopSearchUsecaseInteractor @Inject() (
    shopSearchRepository: ShopSearchRepository,
)(implicit
    ex: ExecutionContext
) extends ShopSearchUsecaseInputPort {
  override def handle(input: ShopSearchInputData): Future[( Seq[Shop], Seq[ (Int, String) ] )]= {

    for{
      //ユースケースに定義しているinputケースクラスを使っていいのか、あとで老人確認する
      shops <- shopSearchRepository.fetch(input)
      images <- shopSearchRepository.fetchImage(shops.map(_.id))
    } yield {
     val imagesFiltered =  images.filter(_._2.isDefined).map(a => (a._1, a._2.get))
     val output = imagesFiltered.map { image =>
//        バイナリ文字列に変換
        ( image._1,  "data:image/jpg;base64,"+Base64.getEncoder().encodeToString(image._2))

      }
      (shops, output)
    }

  }
}
