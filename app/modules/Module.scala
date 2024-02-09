package modules

import com.google.inject.AbstractModule
import domains.repositories.{ShopDetailRepository, ShopSearchRepository}
import play.api.{Configuration, Environment}
import domains.usecases._
import infrastructures.db.MySQLShopSearchRepository
//import infrastructures.db.{ MySQLShopDetailRepository}
class Module(
    environment: Environment,
    configuration: Configuration
) extends AbstractModule {
  override def configure(): Unit = {
    // @formatter:off
    bind(classOf[ShopSearchUsecaseInputPort]).to(classOf[ShopSearchUsecaseInteractor])
//    bind(classOf[ShopDetailUsecaseInputPort]).to(classOf[ShopDetailUsecaseInteractor])
    bind(classOf[ShopSearchRepository]).to(classOf[MySQLShopSearchRepository])
//    bind(classOf[ShopDetailRepository]).to(classOf[MySQLShopDetailRepository])
  }
}
