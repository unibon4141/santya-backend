package modules

import com.google.inject.AbstractModule
import domains.repositories.{ShopCommentRepository, ShopDetailRepository, ShopManagementRepository, ShopSearchRepository}
import play.api.{Configuration, Environment}
import domains.usecases._
import infrastructures.db.{MySQLShopCommentRepository, MySQLShopDetailRepository, MySQLShopManagementRepository, MySQLShopSearchRepository}
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
    bind(classOf[ShopManagementUsecaseInputPort]).to(classOf[ShopManagementUsecaseInteracter])

//    リポジトリ
    bind(classOf[ShopSearchRepository]).to(classOf[MySQLShopSearchRepository])
    bind(classOf[ShopDetailRepository]).to(classOf[MySQLShopDetailRepository])
    bind(classOf[ShopCommentRepository]).to(classOf[MySQLShopCommentRepository])
    bind(classOf[ShopManagementRepository]).to(classOf[MySQLShopManagementRepository])
  }
}
