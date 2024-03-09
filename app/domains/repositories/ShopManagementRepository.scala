package domains.repositories

import domains.usecases.{ShopManagementEditInputData, ShopManagementInputData}
import entities.ShopId

import scala.concurrent.Future

trait ShopManagementRepository {
  def addShop(input: ShopManagementInputData)
  def editShop(input: ShopManagementEditInputData)
  def existShopByShopName(shopName: String): Future[Boolean]

  def isChangedShopNameById(shopId: ShopId, shopName: String): Future[Boolean]
}
