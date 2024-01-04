package infrastructures.db

import domains.repositories.ShopSearchRepository
import entities.{Address, GenreId, MapId, PriceRangeId, SceneId, Shop, ShopId}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import scala.concurrent.{ExecutionContext, Future}
import slickSchema.Tables._
import slick.jdbc.MySQLProfile.api._
import slick.jdbc.JdbcProfile
import javax.inject.Inject

class MySQLShopSearchRepository @Inject() (
    protected val dbConfigProvider: DatabaseConfigProvider
)(implicit ec: ExecutionContext)
    extends ShopSearchRepository
    with HasDatabaseConfigProvider[JdbcProfile] {
  def fetch(): Future[Seq[Shop]] = {
    val action = Shops.result
    db.run(action)
    for {
      rows <- db.run(action)
    } yield rows.map { shop =>
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
        lunchPriceRangeId = shop.lunchPriceRangeId match {
          case Some(value) => Some(PriceRangeId(value))
          case None        => None
        },
        dinnerPriceRangeId = shop.dinnerPriceRangeId match {
          case Some(value) => Some(PriceRangeId(value))
          case None        => None
        },
        shopAddress = Address(shop.shopAddress),
        distance = shop.distance,
        lastUpdateAt = shop.updatedTime.toLocalDateTime
      )
    }
  }
}
