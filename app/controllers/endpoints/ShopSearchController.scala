package controllers.endpoints

import play.api.mvc._

import javax.inject._
import scala.concurrent.ExecutionContext
import domains.usecases.{ShopSearchInputData, ShopSearchUsecaseInputPort}
import entities.ShopId
import io.circe.syntax._
import io.circe.generic.auto._
import play.api.libs.circe.Circe

//店舗一覧の表示用コントローラー
//TODO ワード検索、シーン検索ができるように改修する
@Singleton
class ShopSearchController @Inject() (
    ShopSearchUsecase: ShopSearchUsecaseInputPort,
    val controllerComponents: ControllerComponents
)(implicit ec: ExecutionContext)
    extends BaseController
    with Circe {
  def index(): Action[AnyContent] = Action {
    implicit request: Request[AnyContent] =>
      val k = 10
      println("aa")
      Ok("ok")
  }

  def slickTest(id: Int): Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent] =>
      val input = ShopSearchInputData(ShopId(id))
      for {
        output <- ShopSearchUsecase.handle(input)
      } yield Ok(output.toString())
  }

  case class Foo(foo: String)

  def example = Action(circe.json[Foo]) { implicit request =>
    val isEqual = request.body
    case class TestA(msg: String)
    Ok(TestA("example ok").asJson)
  }
}
