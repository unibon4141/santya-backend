package domains.repositories
import entities.{Shop, ShopId, UserId}

import scala.concurrent.Future
trait UserRepository {
  def fetch(username: String, password: String): Future[Int]

  def signUp(username: String, password: String): Future[Int]

  def existUserByUsername(username: String): Future[Boolean]
  def getIdByUsername(username: String): Future[Int]
  def getFavoriteShops(userId: UserId): Future[Seq[Shop]]
  def existFavoriteShop(userId: UserId, shopId: ShopId): Future[Boolean]
  def addFavoriteShop(userId: UserId, shopId: ShopId): Future[Boolean]
  def removeFavoriteShop(userId: UserId, shopId: ShopId): Future[Boolean]
}
