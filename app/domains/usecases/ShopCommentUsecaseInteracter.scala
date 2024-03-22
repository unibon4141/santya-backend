package domains.usecases

import domains.repositories.ShopCommentRepository
import entities.{ShopComment, ShopId}

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}



@Singleton
class ShopCommentUsecaseInteracter @Inject() (
                                              shopCommentRepository: ShopCommentRepository
                                            )(implicit
                                              ex: ExecutionContext
                                            ) extends ShopCommentUsecaseInputPort {
  def handle(input: ShopId):Future[Seq[ShopComment]] = {
    shopCommentRepository.fetch(input)
  }

  def post(input: ShopPostCommentInputData): Future[Boolean] = {
    shopCommentRepository.postComment(input)
  }
}
