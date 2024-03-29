package domains.usecases

import domains.repositories.ShopImageRepository
import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
@Singleton
class ShopImageUsecaseInteracter @Inject()(
                                              shopImageRepository: ShopImageRepository
                                            )(implicit
                                              ex: ExecutionContext
                                            ) extends ShopImageUsecaseInputPort {
  def handle(input: ShopImageInputData): Future[Int] = {
    shopImageRepository.fetch(input.paths, input.shopId)
  }

  def handleBinary(input: ShopImageBinaryInputData): Future[Int] = {
    shopImageRepository.saveImage( input.shopId, input.content)
  }
}