package modules

import com.google.inject.AbstractModule
import domains.repositories.{ShopDetailRepository, ShopSearchRepository}
import play.api.{Configuration, Environment}
import domains.usecases._
import infrastructures.db.{MySQLShopCommentRepository, MySQLShopDetailRepository, MySQLShopSearchRepository}
//import infrastructures.db.{ MySQLShopDetailRepository}
class Module(
    environment: Environment,
    configuration: Configuration
) extends AbstractModule {
  override def configure(): Unit = {
    // @formatter:off
//    ユースケース
    bind(classOf[ShopSearchUsecaseInputPort]).to(classOf[ShopSearchUsecaseInteractor])
    bind(classOf[ShopDetailUsecaseInputPort]).to(classOf[ShopDetailUsecaseInteractor])
    bind(classOf[ShopCommentUsecaseInputPort]).to(classOf[MySQLShopCommentRepository])

//    リポジトリ
    bind(classOf[ShopSearchRepository]).to(classOf[MySQLShopSearchRepository])
    bind(classOf[ShopDetailRepository]).to(classOf[MySQLShopDetailRepository])
  }
}
