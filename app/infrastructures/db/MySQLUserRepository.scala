package infrastructures.db
import domains.repositories.UserRepository
import entities.{Address, Genre, GenreId, MapId, PriceRange, Scene, SceneId, Shop, ShopId, UserId}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}

import scala.concurrent.{ExecutionContext, Future}
import slickSchema.{Tables => T}
import slick.jdbc.MySQLProfile.api._
import slick.jdbc.JdbcProfile

import java.time.LocalDateTime
import javax.inject.Inject
class MySQLUserRepository @Inject() (
                                            protected val dbConfigProvider: DatabaseConfigProvider
                                          )(implicit ec: ExecutionContext)
  extends UserRepository
    with HasDatabaseConfigProvider[JdbcProfile] {


  def fetch(username: String, password: String): Future[Int] = {
    val action = T.Users.filter(u => u.userName === username && u.password === password).result
      db.run(action).map{result =>
        if(result.nonEmpty) {
          result.head.userId
        } else {
          0
        }
      }
  }

  def signUp(username: String, password: String): Future[Int] = {
    val action = T.Users.map(u => (u.userName,u.password )) += (username, password)
    db.run(action)
  }

  def existUserByUsername(username: String): Future[Boolean] = {
    val action = T.Users.filter(u => u.userName === username).result
    db.run(action).map {
      result =>
        if(result.nonEmpty) {
          true
        } else {
          false
        }
    }
  }

  def getIdByUsername(username: String): Future[Int] = {
    val action = T.Users.filter(u => u.userName === username).result
    db.run(action).map {
      result =>
        result.head.userId
    }
  }

  def getFavoriteShops(userId: UserId): Future[Seq[Shop]] = {
    val action = {
      (for {
        favoriteShopIds <- T.Favoriteshop.filter(f => f.userId === userId.value)
        shops <- T.Shops.filter(s => s.shopId === favoriteShopIds.shopId)
        genres <- T.Genre if shops.genreId === genres.genreId
      } yield (shops, genres)).result
    }
    for {
      rows <- db.run(action)
    } yield rows.map {
      case (shop, genre) =>
        //DBから取得した結果をShopエンティティに格納
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
  def existFavoriteShop(userId: UserId, shopId: ShopId): Future[Boolean] = {
    val action = {
      T.Favoriteshop.filter(f => f.userId === userId.value && f.shopId === shopId.value).result
    }
    db.run(action).map{ result =>
      result.headOption match {
        case Some(_) => true
        case None => false
      }
    }
  }
  def addFavoriteShop(userId: UserId, shopId: ShopId): Future[Boolean] = {
    val action = {
      T.Favoriteshop.map(f => (f.userId, f.shopId)) += (userId.value, shopId.value)
    }
    db.run(action).map { result =>
      if(result > 0) true else false
    }
  }
  def removeFavoriteShop(userId: UserId, shopId: ShopId): Future[Boolean] = {
    val action = {
      T.Favoriteshop.filter(f => f.userId === userId.value && f.shopId === shopId.value)
        .delete
    }
    db.run(action).map{ result =>
      if(result > 0) true else false
    }
  }
}
