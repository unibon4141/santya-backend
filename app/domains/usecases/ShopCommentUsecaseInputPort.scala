package domains.usecases

import entities.ShopId

trait ShopCommentUsecaseInputPort {
  def handle(shopId: ShopId)
}

  case class ShopCommentInputData(
                                 shopId: ShopId
                                 )