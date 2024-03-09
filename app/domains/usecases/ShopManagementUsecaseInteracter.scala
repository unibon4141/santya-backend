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

  def edit(input: ShopManagementEditInputData):Future[Boolean] = {
    for{
      isShopNameChanged <- shopManagementRepository.isChangedShopNameById(input.shopId, input.shopName)
      existShop <- shopManagementRepository.existShopByShopName(input.shopName)
    } yield {
      //店舗名が更新されていないか、更新されていても重複する店舗がなかった場合
      if(!isShopNameChanged || !existShop) {
//        TODO: ユースケース、リポジトリのメソッドの名前を基本同じにしているが、それぞれもっと適切な名前の付け方があるのでは？
        shopManagementRepository.editShop(input)
        true
      }  else {
        // 店舗追加できたかのステータスを返したい
        false
      }
    }
  }
}
