package controllers.endpoints

import infrastructures.db.dao.Dao
import play.api.mvc._
import javax.inject._
import scala.concurrent.ExecutionContext
import domains.usecases.ShopSearchInputData
import entities.ShopId
//店舗一覧の表示用コントローラー
@Singleton
class ShopSearchController @Inject() (
    dao: Dao,
    val controllerComponents: ControllerComponents
)(implicit ec: ExecutionContext)
    extends BaseController {

  def index(): Action[AnyContent] = Action {
    implicit request: Request[AnyContent] =>
      Ok("ok")
  }
  def slickTest(id: Int): Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent] =>
      val input = ShopSearchInputData(ShopId(id))
      for {
        rst <- dao.fetch()
      } yield {
        Ok(rst.toString())
      }
  }
}
