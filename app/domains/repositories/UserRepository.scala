package domains.repositories
import scala.concurrent.Future
trait UserRepository {
  def fetch(username: String, password: String): Future[Int]

}
