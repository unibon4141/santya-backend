package infrastructures.db
import domains.repositories.UserRepository
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import scala.concurrent.{ExecutionContext, Future}
import slickSchema.{Tables => T}
import slick.jdbc.MySQLProfile.api._
import slick.jdbc.JdbcProfile

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
}
