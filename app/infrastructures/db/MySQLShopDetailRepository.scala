package infrastructures.db

import domains.repositories.ShopDetailRepository
import entities.{Address, GenreId, MapId, PriceRangeId, SceneId, Shop, ShopId}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slickSchema.Tables.Shops
import slick.jdbc.MySQLProfile.api._
import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class MySQLShopDetailRepository @Inject() (
    protected val dbConfigProvider: DatabaseConfigProvider
)(implicit ec: ExecutionContext)
    extends ShopDetailRepository
    with HasDatabaseConfigProvider[JdbcProfile] {
//  shopIdで店舗の詳細情報を取ってくるメソッド
  def fetch(id: ShopId): Future[Shop] = {
    val action = Shops
      .filter(_.shopId === id.value)
      .filter(_.deleteFlg === false)
      .result

    (for {
      row <- db.run(action)
    } yield row.head).map { shop =>
      val sceneIds: Seq[SceneId] = Seq(shop.sceneId1, shop.sceneId2).flatMap {
        case Some(id) => Some(SceneId(id))
        case None     => None
      }
      Shop(
        id = ShopId(shop.shopId.get),
        name = shop.shopName,
        mapId = MapId(shop.mapId),
        genreId = GenreId(shop.genreId),
        sceneIds = sceneIds,
        lunchPriceRangeId = PriceRangeId(shop.lunchPriceRangeId),
        dinnerPriceRangeId = PriceRangeId(shop.dinnerPriceRangeId),
        shopAddress = Address(shop.shopAddress),
        distance = shop.distance,
        lastUpdateAt = shop.updatedTime.toLocalDateTime
      )
    }
  }
}
