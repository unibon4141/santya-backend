package modules

import com.google.inject.AbstractModule
import domains.repositories.{ShopCommentRepository, ShopDetailRepository, ShopImageRepository, ShopManagementRepository, ShopSearchRepository, UserRepository}
import play.api.{Configuration, Environment}
import domains.usecases._
import infrastructures.db.{MySQLShopCommentRepository, MySQLShopDetailRepository, MySQLShopImageRepository, MySQLShopManagementRepository, MySQLShopSearchRepository, MySQLUserRepository}

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
    bind(classOf[UserUsecaseInputPort]).to(classOf[UserUsecaseInteracter])
    bind(classOf[ShopImageUsecaseInputPort]).to(classOf[ShopImageUsecaseInteracter])

//    リポジトリ
    bind(classOf[ShopSearchRepository]).to(classOf[MySQLShopSearchRepository])
    bind(classOf[ShopDetailRepository]).to(classOf[MySQLShopDetailRepository])
    bind(classOf[ShopCommentRepository]).to(classOf[MySQLShopCommentRepository])
    bind(classOf[ShopManagementRepository]).to(classOf[MySQLShopManagementRepository])
    bind(classOf[UserRepository]).to(classOf[MySQLUserRepository])
    bind(classOf[ShopImageRepository]).to(classOf[MySQLShopImageRepository])
  }
}
