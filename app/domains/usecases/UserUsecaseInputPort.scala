package domains.usecases
import com.google.inject.Singleton
import entities.{Shop, ShopId, UserId}

import scala.concurrent.Future
trait UserUsecaseInputPort {
  def login(input: UserLoginData): Future[UserId]
  def signUp(input: UserSignUpData): Future[Int]
  def getFavoriteShops(input: getFavoriteShopsData): Future[Seq[Shop]]
  def toggleFavoriteShop(input: toggleFavoriteShopData): Future[Int]

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
case class toggleFavoriteShopData(
                                 userId: UserId,
                                 shopId: ShopId
                               )