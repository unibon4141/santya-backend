package domains.usecases

import controllers.requests.ShopCommentRequest
import entities.{ShopComment, ShopId, UserId}

import scala.concurrent.Future

trait ShopCommentUsecaseInputPort {
  def handle(shopId: ShopId): Future[Seq[ShopComment]]

  def post(input: ShopPostCommentInputData): Future[Boolean]
}

final case class ShopFetchCommentInputData(
                                 shopId: ShopId
                                 )

final case class ShopPostCommentInputData(
                                           shopId: ShopId,
                                           userId: UserId,
                                           title: String,
                                           sentence: String
                                         )