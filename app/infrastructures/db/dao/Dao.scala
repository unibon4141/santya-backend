package infrastructures.db.dao

import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.Future

import slick.jdbc.MySQLProfile.api._
import javax.inject._

import scala.concurrent.ExecutionContext

import slickSchema.Tables._
@Singleton
class Dao @Inject() (
    protected val dbConfigProvider: DatabaseConfigProvider
)(implicit ec: ExecutionContext)
    extends HasDatabaseConfigProvider[JdbcProfile] {

  //  店舗情報を取得するメソッド
  def fetch(): Future[Seq[ShopsRow]] = {
    // SELECTクエリ
    val action = Shops.result

    //      if(condition.getOrElse(null).scene != 0) {
    //      condition.fold(Shops.to[Seq]) { c =>
    //        Shops.filter(n => (c.scene === n.sceneId1 ))
    //      }.sortBy(_.shopId).result
    //    } else {

    //    }
    // クエリ発行
    db.run(action)
//    db.run(action).recover { case NonFatal(e) =>
//      logger.error("DB操作で例外が発生しました。", e)
//      throw e
//    }
  }

}
