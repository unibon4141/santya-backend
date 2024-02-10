package infrastructures.db

import domains.repositories.ShopSearchRepository
import entities.{Address, Genre, GenreId, MapId, PriceRange, PriceRangeId, Scene, SceneId, Shop, ShopId}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}

import scala.concurrent.{ExecutionContext, Future}
import slickSchema.{Tables => T}
import slick.jdbc.MySQLProfile.api._
import slick.jdbc.JdbcProfile

import javax.inject.Inject

class MySQLShopSearchRepository @Inject() (
    protected val dbConfigProvider: DatabaseConfigProvider
)(implicit ec: ExecutionContext)
    extends ShopSearchRepository
    with HasDatabaseConfigProvider[JdbcProfile] {

//  全店舗を取得するメソッド
  def fetch(): Future[Seq[Shop]] = {
    val action = (for {
      shops <- T.Shops
      genres <- T.Genre if shops.genreId === genres.genreId
    } yield (shops, genres)
      ).result
    db.run(action)

    for {
      rows <- db.run(action)
    } yield rows.map { case (shop, genre) =>

      Shop(
        id = ShopId(shop.shopId),
        name = shop.shopName,
        mapId = Option(MapId(shop.mapId)),
        genre = Genre(
          GenreId(genre.genreId), genre.genreName
        ),
        scenes = Seq(shop.sceneId1, shop.sceneId2).flatMap( v => v match {
          case Some(a) => Scene.of(a)
          case None    => None
        }),
        lunchPriceRange = shop.lunchPriceRangeId match {
          case Some(value) => Some(PriceRange.of(value))
          case None        => None
        },
        dinnerPriceRange = shop.dinnerPriceRangeId match {
          case Some(value) => Some(PriceRange.of(value))
          case None => None
        },
        shopAddress = Option(Address(shop.shopAddress)),
        distance = shop.distance,
        createAt = shop.createdTime.toLocalDateTime,
        updatedAt = shop.updatedTime.toLocalDateTime
      )
    }
  }
}
