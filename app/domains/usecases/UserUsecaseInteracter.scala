package domains.usecases

import domains.repositories.UserRepository
import entities.UserId
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
}

