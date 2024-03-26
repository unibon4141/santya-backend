package infrastructures.db

import domains.repositories.ShopSearchRepository
import domains.usecases.ShopSearchInputData
import entities.{Address, Genre, GenreId, ImageFile, MapId, PriceRange, Scene, Shop, ShopId}
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

//  店舗の検索メソッド
//ワード検索とシーン検索ができる
  def fetch(input: ShopSearchInputData): Future[Seq[Shop]] = {
    val action = (input.word, input.sceneId) match {
//ワード検索＋シーンでの検索
      case (Some(w), Some(s)) => (for {
        shops <- T.Shops.filter(shop => (shop.shopName like s"%$w%") && (shop.sceneId1 === s || shop.sceneId2 === s))
        genres <- T.Genre if shops.genreId === genres.genreId
      } yield (shops, genres)
        ).result
//ワード検索のみ
      case (Some(w), None) => (for {
        shops <- T.Shops.filter(shop => shop.shopName like s"%$w%")
        genres <- T.Genre if shops.genreId === genres.genreId
      } yield (shops, genres)
        ).result
//シーン検索のみ
      case (None, Some(s)) => (for {
        shops <- T.Shops.filter(shop =>shop.sceneId1 === s || shop.sceneId2 === s)
        genres <- T.Genre if shops.genreId === genres.genreId
      } yield (shops, genres)
        ).result
//ワードとシーンが未入力の場合は、全店舗を取得する
      case _ => (for {
        shops <- T.Shops
        genres <- T.Genre if shops.genreId === genres.genreId
      } yield (shops, genres)
        ).result
    }

    for {
      rows <- db.run(action)
    } yield rows.map { case (shop, genre) =>
//      DBから取得した結果をShopエンティティに格納
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

  def fetchImage(shopIds: Seq[ShopId]): Future[Seq[(Int, String)]] = {
    val ids = shopIds.map(_.value)
    val action = T.Shopimage.filter(image => image.shopId inSet ids).result
    db.run(action).map {aa =>
     aa.map(toEntity)

    }

  }

  private def toEntity(input: T.Shopimage#TableElementType): (Int, String) = {
    (input.shopId, input.imagePath)
  }

}
