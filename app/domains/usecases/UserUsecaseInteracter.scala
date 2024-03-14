package domains.usecases

import domains.repositories.UserRepository
import entities.{Shop, UserId}

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UserUsecaseInteracter @Inject()(
                                       userRepository: UserRepository,
                                     )(implicit
                                       ex: ExecutionContext
                                     ) extends UserUsecaseInputPort {
  //  TODO 返り値はEitherとかの成功、失敗がわかる型にしたい
  def login(input: UserLoginData): Future[UserId] = {
    for {
      //ユースケースに定義しているinputケースクラスを使っていいのか、あとで老人確認する
      result <- userRepository.fetch(input.username, input.password)
    } yield {
      UserId(result)
    }
  }

  def signUp(input: UserSignUpData): Future[Int] = {
//    TODO flatMapを使ってFutureを外してる処理の流れがイマイチ理解できていない
    userRepository.existUserByUsername(input.username).flatMap {
      case true => Future.successful(0)
      case false =>
      for {
           _ <- userRepository.signUp(input.username, input.password)
          userId <- userRepository.getIdByUsername(input.username)
        } yield userId
    }
  }

  def getFavoriteShops(input: getFavoriteShopsData): Future[Seq[Shop]] = {
    for {
      shops <- userRepository.getFavoriteShops(input.userId)
    } yield shops
  }

  def toggleFavoriteShop(input: toggleFavoriteShopData): Future[Int] = {
    userRepository.existFavoriteShop(input.userId, input.shopId).flatMap {isExist =>
      if(isExist) {
        userRepository.removeFavoriteShop(input.userId, input.shopId).flatMap {removeResult =>
          if(removeResult) Future.successful(2) else Future.successful(4)
        }
      } else {
        userRepository.addFavoriteShop(input.userId, input.shopId).flatMap {addResult =>
          if(addResult) Future.successful(1) else Future.successful(3)
        }
      }
    }
  }

}

