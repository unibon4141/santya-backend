package domains.usecases
import com.google.inject.Singleton
import entities.{Shop, UserId}

import scala.concurrent.Future
trait UserUsecaseInputPort {
  def login(input: UserLoginData): Future[UserId]
  def signUp(input: UserSignUpData): Future[Int]
  def getFavoriteShops(input: getFavoriteShopsData): Future[Seq[Shop]]

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

case class getFavoriteShopsData(
                               userId: UserId
                               )
