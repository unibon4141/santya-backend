package infrastructures.db

import slickSchema.{Tables => T}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.MySQLProfile.api._
import slick.jdbc.JdbcProfile

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}
import domains.repositories.ShopManagementRepository
import domains.usecases.ShopManagementInputData

class MySQLShopManagementRepository @Inject()(
                                               protected val dbConfigProvider: DatabaseConfigProvider
                                             )(implicit ec: ExecutionContext)
  extends ShopManagementRepository
    with HasDatabaseConfigProvider[JdbcProfile] {
  def addShop(input: ShopManagementInputData) = {
    val action =
      //mapIdはnullableにしたい。deleteFlgはデフォルト値にfalseを設定したい
      T.Shops.map { p => (p.shopName, p.genreId, p.sceneId1, p.sceneId2, p.lunchPriceRangeId, p.dinnerPriceRangeId, p.shopAddress, p.distance, p.deleteFlg, p.mapId) } += (input.shopName, input.genreId.value, Option(input.sceneId1.value), Option(input.sceneId2.value), Option(input.lunchPriceRangeId.value), Option(input.dinnerPriceRangeId.value), input.shop_address, input.distance, false, 0)
    db.run(action)

  }


  /**
   * 同名の店舗名を持った店舗が存在するかチェック
   *
   * @param 存在する場合はtrueを返す
   */
  def existShopByShopName(shopName: String): Future[Boolean] = {
    val action =
      T.Shops.filter(s => s.shopName === shopName)
        .result
    db.run(action).map { result =>
      if (result.nonEmpty) {
        true
      } else {
        false
      }
    }
  }

}
