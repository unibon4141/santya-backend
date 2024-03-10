package domains.usecases
import com.google.inject.Singleton
import entities.UserId

import scala.concurrent.Future
trait UserUsecaseInputPort {
  def login(input: UserLoginData): Future[UserId]

}

@Singleton
object UserUsecaseInputPort {}
case class UserLoginData(
                                username: String,
                                password: String
                              )
