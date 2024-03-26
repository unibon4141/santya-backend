package infrastructures.db

import domains.repositories.{ShopDetailRepository, ShopImageRepository}
import entities.{Address, Genre, GenreId, ImageFile, MapId, PriceRange, Scene, SceneId, Shop, ShopId}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import slickSchema.{Tables => T}
import slick.jdbc.MySQLProfile.api._

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class MySQLShopImageRepository @Inject() (
                                            protected val dbConfigProvider: DatabaseConfigProvider
                                          )(implicit ec: ExecutionContext)
  extends ShopImageRepository
    with HasDatabaseConfigProvider[JdbcProfile] {
  //  shopIdで店舗の詳細情報を取ってくるメソッド
  def fetch(paths: Seq[ImageFile], shopId: ShopId ): Future[Int] = {
    val states = paths.map (p => (p.path, shopId.value))
    val action = {
      T.Shopimage.map(si=> (si.imagePath, si.shopId) ) ++= states
    }
    db.run(action).map {
      case Some(num) => num
      case _ => 0
    }
  }
}
