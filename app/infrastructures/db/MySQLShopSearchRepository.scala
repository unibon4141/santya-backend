package infrastructures.db

import domains.repositories.ShopSearchRepository
import domains.usecases.{ShopDetailInputData, ShopSearchInputData}
import entities.{Address, Genre, GenreId, MapId, PriceRange, Scene, Shop, ShopId}
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

  def fetchByWord(input: ShopSearchInputData): Future[Seq[Shop]] = {
    val action = (input.word, input.sceneId) match {
      case (Some(w), Some(s)) => (for {
        shops <- T.Shops.filter(shop => (shop.shopName like s"%$w%") && (shop.sceneId1 === s || shop.sceneId2 === s))
        genres <- T.Genre if shops.genreId === genres.genreId
      } yield (shops, genres)
        ).result

      case (Some(w), None) => (for {
        shops <- T.Shops.filter(shop => shop.shopName like s"%$w%")
        genres <- T.Genre if shops.genreId === genres.genreId
      } yield (shops, genres)
        ).result

      case (None, Some(s)) => (for {
        shops <- T.Shops.filter(shop =>shop.sceneId1 === s || shop.sceneId2 === s)
        genres <- T.Genre if shops.genreId === genres.genreId
      } yield (shops, genres)
        ).result

      case _ => (for {
        shops <- T.Shops
        genres <- T.Genre if shops.genreId === genres.genreId
      } yield (shops, genres)
        ).result
    }

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
        scenes = Seq(shop.sceneId1, shop.sceneId2).flatten.flatMap(Scene.of),
        lunchPriceRange = shop.lunchPriceRangeId.map(PriceRange.of),
        dinnerPriceRange = shop.dinnerPriceRangeId.map(PriceRange.of),
        shopAddress = Option(Address(shop.shopAddress)),
        distance = shop.distance,
        createAt = shop.createdTime.toLocalDateTime,
        updatedAt = shop.updatedTime.toLocalDateTime
      )
    }
  }
}
