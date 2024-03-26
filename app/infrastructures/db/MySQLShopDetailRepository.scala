package infrastructures.db

import domains.repositories.ShopDetailRepository
import entities.{Address, Genre, GenreId, MapId, PriceRange, Scene, SceneId, Shop, ShopId}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slickSchema.{Tables => T}
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
    val action = (for {
      shops <- T.Shops
        .filter(_.shopId === id.value)
        .filter(_.deleteFlg === false)
        genres <- T.Genre if shops.genreId === genres.genreId
    } yield (shops, genres)).result

    (for {
      row <- db.run(action)
    } yield row.head).map { case (shop, genre) =>
      Shop(
        id = ShopId(shop.shopId),
        name = shop.shopName,
        mapId = Option(MapId(shop.mapId)),
        genre = Genre(
          GenreId(genre.genreId), genre.genreName
        ),
        scenes = Seq(shop.sceneId1, shop.sceneId2).flatten.flatMap(Scene.of),
        lunchPriceRange = shop.lunchPriceRangeId.map(PriceRange.of),
        dinnerPriceRange = shop.dinnerPriceRangeId.map(PriceRange.of),
        shopAddress = Option(Address(shop.shopAddress)),
        distance = shop.distance,
        //暫定的
        createAt = shop.createdTime.toLocalDateTime,
        updatedAt = shop.updatedTime.toLocalDateTime
      )
    }
  }
}
