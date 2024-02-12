package controllers.endpoints

import controllers.responses.{SceneResponse, ShopResponse}
import entities.Scene
import io.circe.syntax.EncoderOps
import play.api.mvc._

import javax.inject._
import scala.concurrent.ExecutionContext
import play.api.libs.circe.Circe
import io.circe.syntax._
import io.circe.generic.auto._

@Singleton
class SceneController @Inject() (
                                       val controllerComponents: ControllerComponents
                                     )(implicit ec: ExecutionContext)
  extends BaseController
    with Circe {

//全シーンを取得する
  def index(): Action[AnyContent] = Action {
    implicit request: Request[AnyContent] =>

        Ok(SceneResponse.make(Scene.makeSceneList).asJson)
  }

}

