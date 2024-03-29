package domains.usecases

import com.google.inject.Singleton
import entities.{Shop, ShopId}

import scala.concurrent.Future

trait ShopSearchUsecaseInputPort {
  def handle(input: ShopSearchInputData): Future[(Seq[Shop], Seq[(Int, String)])]

}

@Singleton
object ShopSearchUsecaseInputPort {}
case class ShopSearchInputData(
    word: Option[String],
    sceneId: Option[Int]
)
