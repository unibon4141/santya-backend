package domains.repositories

import domains.usecases.ShopManagementInputData

import scala.concurrent.Future

trait ShopManagementRepository {
  def addShop(input: ShopManagementInputData)
  def existShopByShopName(shopName: String): Future[Boolean]
}
