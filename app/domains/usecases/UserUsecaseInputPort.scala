package domains.usecases
import com.google.inject.Singleton
import entities.UserId

import scala.concurrent.Future
trait UserUsecaseInputPort {
  def login(input: UserLoginData): Future[UserId]
  def signUp(input: UserSignUpData): Future[Int]

}

@Singleton
object UserUsecaseInputPort {}
case class UserLoginData(
                                username: String,
                                password: String
                              )

case class UserSignUpData(
                          username: String,
                          password: String
                        )
