package domains.repositories
import entities.{Shop, UserId}

import scala.concurrent.Future
trait UserRepository {
  def fetch(username: String, password: String): Future[Int]

  def signUp(username: String, password: String): Future[Int]

  def existUserByUsername(username: String): Future[Boolean]
  def getIdByUsername(username: String): Future[Int]
  def getFavoriteShops(userId: UserId): Future[Seq[Shop]]
}
