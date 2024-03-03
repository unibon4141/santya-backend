package modules

import com.google.inject.AbstractModule
import domains.repositories.{ShopCommentRepository, ShopDetailRepository, ShopSearchRepository}
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
    bind(classOf[ShopCommentUsecaseInputPort]).to(classOf[ShopCommentUsecaseInteracter])

//    リポジトリ
    bind(classOf[ShopSearchRepository]).to(classOf[MySQLShopSearchRepository])
    bind(classOf[ShopDetailRepository]).to(classOf[MySQLShopDetailRepository])
    bind(classOf[ShopCommentRepository]).to(classOf[MySQLShopCommentRepository])
  }
}
