package modules

import com.google.inject.AbstractModule
import domains.repositories.ShopSearchRepository
import play.api.{Configuration, Environment}
import domains.usecases._
import infrastructures.db.MySQLShopSearchRepository
class Module(
    environment: Environment,
    configuration: Configuration
) extends AbstractModule {
  override def configure(): Unit = {
    // @formatter:off
    bind(classOf[ShopSearchUsecaseInputPort]).to(classOf[ShopSearchUsecaseInteractor])
    bind(classOf[ShopSearchRepository]).to(classOf[MySQLShopSearchRepository])
  }
}
