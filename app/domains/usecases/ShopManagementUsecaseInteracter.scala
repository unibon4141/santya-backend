package domains.usecases

import domains.repositories.{ShopCommentRepository, ShopManagementRepository}
import entities.{Shop, ShopComment, ShopId}

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ShopManagementUsecaseInteracter @Inject() (
                                               shopManagementRepository: ShopManagementRepository
                                             )(implicit
                                               ex: ExecutionContext
                                             ) extends ShopManagementUsecaseInputPort {
  def handle(input: ShopManagementInputData):Future[Boolean] = {
    for{
      existShop <- shopManagementRepository.existShopByShopName(input.shopName)
    } yield {
      if(!existShop) {
        shopManagementRepository.addShop(input)
        true
      }  else {
        // 店舗追加できたかのステータスを返したい
        false
      }
    }

  }
}
