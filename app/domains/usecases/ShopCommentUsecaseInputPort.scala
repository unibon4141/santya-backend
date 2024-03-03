package domains.usecases

import entities.{ShopComment, ShopId}

import scala.concurrent.Future

trait ShopCommentUsecaseInputPort {
  def handle(shopId: ShopId): Future[Seq[ShopComment]]
}

  case class ShopCommentInputData(
                                 shopId: ShopId
                                 )