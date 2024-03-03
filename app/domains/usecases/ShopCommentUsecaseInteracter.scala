package domains.usecases

import domains.repositories.ShopCommentRepository
import entities.{Shop, ShopComment, ShopId}
import views.html.helper.input

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
}
