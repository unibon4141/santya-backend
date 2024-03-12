package controllers.endpoints

import controllers.responses.ShopResponse
import play.api.mvc._

import javax.inject._
import scala.concurrent.{ExecutionContext, Future}
import domains.usecases.{ShopSearchInputData, ShopSearchUsecaseInputPort}
import io.circe.syntax._
import io.circe.generic.auto._
import play.api.libs.circe.Circe

@Singleton
class TestAdd @Inject() (
//                                       ShopSearchUsecase: ShopSearchUsecaseInputPort,
//                                       val controllerComponents: ControllerComponents,
                                       parser: BodyParsers.Default
                                     )(implicit ec: ExecutionContext)
  extends ActionBuilderImpl(parser)
     {

  //  全店舗を取得する
  override def invokeBlock[A](request: Request[A], block: Request[A] => Future[Result]): Future[Result] = {
    println("invoke exe")
    block(request)
  }
}
