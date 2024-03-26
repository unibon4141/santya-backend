package controllers.endpoints

import controllers.requests.{ShopManagementEditRequest, ShopManagementRequest}

import javax.inject._
import scala.concurrent.{ExecutionContext, Future}
import domains.usecases.{ShopImageInputData, ShopImageUsecaseInputPort, ShopManagementEditInputData, ShopManagementInputData, ShopManagementUsecaseInputPort}
import entities.{GenreId, ImageFile, PriceRangeId, SceneId, ShopId}
import io.circe.syntax._
import io.circe.generic.auto._
import play.api.libs.Files
import play.api.libs.circe.Circe
import play.api.mvc._

import java.nio.file.Paths
import java.nio.file.{Files => F}
import java.text.SimpleDateFormat
import java.util.Date
import scala.reflect.io.File

//店舗の追加丶編集ができるコントローラー
@Singleton
class ShopManagementController @Inject()(
                                          ShopManagementUsecase: ShopManagementUsecaseInputPort,
                                          shopImageUsecase: ShopImageUsecaseInputPort,
                                          val controllerComponents: ControllerComponents,
                                          auth: Auth
                                        )(implicit ec: ExecutionContext)
  extends BaseController
    with Circe {
  def index() = {
    auth(circe.json[ShopManagementRequest]).async {
          implicit request =>
            val input = ShopManagementInputData(
              shopName = request.body.shop_name,
              genreId = GenreId(request.body.genre_id),
              sceneId1 = SceneId(request.body.scene_id1),
              sceneId2 = SceneId(request.body.scene_id2),
              lunchPriceRangeId = PriceRangeId(request.body.lunch_price_range_id),
              dinnerPriceRangeId = PriceRangeId(request.body.dinner_price_range_id),
              shop_address = request.body.shop_address,
              distance = request.body.distance
            )
            ShopManagementUsecase.handle(input).map { result =>
              //店舗の追加に成功した場合
              if (result) {
                Status(200)
              } else {
                //店舗名の重複があった場合は、エラーを返す
                Status(409)
              }
            }
        }

    }

  def edit(shopId: Int) =
    auth(circe.json[ShopManagementEditRequest]).async {
    implicit request =>
      val input = ShopManagementEditInputData(
        shopId = ShopId(request.body.shop_id),
        shopName = request.body.shop_name,
        genreId = GenreId(request.body.genre_id),
        sceneId1 = SceneId(request.body.scene_id1),
        sceneId2 = SceneId(request.body.scene_id2),
        lunchPriceRangeId = PriceRangeId(request.body.lunch_price_range_id),
        dinnerPriceRangeId = PriceRangeId(request.body.dinner_price_range_id),
        shop_address = request.body.shop_address,
        distance = request.body.distance
      )
      println("ok")
      ShopManagementUsecase.edit(input).map{ result =>
        //店舗の編集に成功した場合
        if(result) {
          Status(200)
        } else {
          //店舗名の重複があった場合は、エラーを返す
          Status(409)
        }
      }
  }

  def upload = Action(parse.multipartFormData).async { request =>
    request.body
      .file("file1")
      .map { picture =>
        val shopId : Int= request.headers.get("Upload-Shop").getOrElse("0").toInt
        println(shopId)

        // 現在の時刻を取得
        val date = new Date();

        // タイムスタンプを文字列に変換するためのフォーマットを指定
        val sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss")

        // タイムスタンプを文字列に変換
        val timestamp = sdf.format(date)
        // only get the last part of the filename
        // otherwise someone can send a path like ../../home/foo/bar.txt to write to other files on the system
//        val filename    = Paths.get(picture.filename).getFileName
        val lastI = picture.filename.lastIndexOf(".")
        var extension = ""
        // 新規ディレクトリ作成
        val dir = Paths.get("./public/images/"+shopId+"/")
        if(F.notExists(dir)) F.createDirectory(dir) // mkdir
        if (lastI > 0) {
          extension = picture.filename.substring(lastI + 1)
        }
        val filename    = Paths.get(timestamp).getFileName+"."+extension
        val path = s"/assets/images/${shopId}/${filename}"
        val fileSize    = picture.fileSize
        val contentType = picture.contentType
        picture.ref.copyTo(Paths.get(s"./public/images/${shopId}/${filename}"), replace = true)
        val input = ShopImageInputData(Seq(ImageFile(path)), ShopId(shopId))
        for {
          result <- shopImageUsecase.handle(input)
        } yield {
          if(result >0 ) {
           Status(200)
          } else {
            Status(400)
          }
        }
      }
      .getOrElse {
//        Redirect(routes.HomeController.index()).flashing("error" -> "Missing file")
        Future.successful(Status(400))
      }
  }
}