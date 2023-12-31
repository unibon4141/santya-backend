package generator
import slick.jdbc.JdbcProfile
import slick.jdbc.meta.MTable
import slick.model.Model

import scala.concurrent.{Await, ExecutionContext}
import scala.concurrent.duration.Duration

object SlickModelGenerator {

  // tableNamesをNoneにすると全テーブル出力
  def run(
      slickDriver: String,
      jdbcDriver: String,
      url: String,
      user: String,
      password: String,
      tableNames: Option[Seq[String]],
      outputDir: String = "app",
      pkg: String = "slickSchema",
      topTraitName: String = "Tables",
      scalaFileName: String = "Tables.scala"
  ) = {
    val driver: JdbcProfile = slick.jdbc.MySQLProfile
    val db = slick.jdbc.MySQLProfile.api.Database
      .forURL(url, driver = jdbcDriver, user = user, password = password)
    try {

      import scala.concurrent.ExecutionContext.Implicits.global
      val mTablesAction = MTable.getTables.map {
        _.map { mTable => mTable.copy(name = mTable.name.copy(catalog = None)) }
      }

//      変更部分
      val modelAction = driver
        .createModel(Some(driver.defaultTables), ignoreInvalidDefaults = false)(
          ExecutionContext.global
        )
        .withPinnedSession
      val allModel = Await.result(db.run(modelAction), Duration.Inf)
//================

      val modelFiltered = tableNames.fold(allModel) { tableNames =>
        Model(tables = allModel.tables.filter { aTable =>
          tableNames.contains(aTable.name.table)
        })
      }

      new SourceCodeGeneratorEx(modelFiltered).writeToFile(
        slickDriver,
        outputDir,
        pkg,
        topTraitName,
        scalaFileName
      )
    } finally db.close
  }

  def main(args: Array[String]): Unit = {
    exportCommonSchema
  }

  // commonスキーマ出力
  def exportCommonSchema = {
    val slickDriver = "slick.jdbc.MySQLProfile"
    val jdbcDriver = "com.mysql.jdbc.Driver"
    val url = "jdbc:mysql://localhost:3306/demo?useSSL=false"
    val user = "root"
    val password = "root"
    val outputDir = "app"
    val pkg = "slickSchema"
    val topTraitName = "Tables"
    val scalaFileName = "Tables.scala"

    // 対象テーブル
    val tableNames: Option[Seq[String]] = Some(
      Seq(
        "shops",
        "priceRange",
        "users",
        "map",
        "genre",
        "scene",
        "comment",
        "favoriteShops",
        "shopImage",
        "menu"
      )
    )

    run(
      slickDriver,
      jdbcDriver,
      url,
      user,
      password,
      tableNames,
      outputDir,
      pkg,
      topTraitName,
      scalaFileName
    )
  }
}
