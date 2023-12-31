package slickSchema
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends Tables {
  val profile = slick.jdbc.MySQLProfile
}

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.jdbc.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Array(Comment.schema, Genre.schema, Map.schema, Menu.schema, Pricerange.schema, Scene.schema, Shopimage.schema, Shops.schema, Users.schema).reduceLeft(_ ++ _)
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Comment
   *  @param shopId Database column shop_id SqlType(INT)
   *  @param userId Database column user_id SqlType(INT)
   *  @param title Database column title SqlType(VARCHAR), Length(20,true)
   *  @param sentence Database column sentence SqlType(VARCHAR), Length(500,true)
   *  @param imageUrl Database column image_url SqlType(VARCHAR), Length(300,true), Default(None)
   *  @param createdTime Database column created_time SqlType(DATETIME)
   *  @param image Database column image SqlType(MEDIUMBLOB), Default(None)
   *  @param commentId Database column comment_id SqlType(INT), AutoInc, PrimaryKey */
  case class CommentRow(shopId: Int, userId: Int, title: String, sentence: String, imageUrl: Option[String] = None, createdTime: java.sql.Timestamp, image: Option[Array[Byte]] = None, commentId: Option[Int] = None)
  /** GetResult implicit for fetching CommentRow objects using plain SQL queries */
  implicit def GetResultCommentRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Option[String]], e3: GR[java.sql.Timestamp], e4: GR[Option[Array[Byte]]], e5: GR[Option[Int]]): GR[CommentRow] = GR{
    prs => import prs._
    val r = (<<?[Int], <<[Int], <<[Int], <<[String], <<[String], <<?[String], <<[java.sql.Timestamp], <<?[Array[Byte]])
    import r._
    CommentRow.tupled((_2, _3, _4, _5, _6, _7, _8, _1)) // putting AutoInc last
  }
  /** Table description of table comment. Objects of this class serve as prototypes for rows in queries. */
  class Comment(_tableTag: Tag) extends profile.api.Table[CommentRow](_tableTag, Some("demo"), "comment") {
    def * = (shopId, userId, title, sentence, imageUrl, createdTime, image, Rep.Some(commentId)).<>(CommentRow.tupled, CommentRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(shopId), Rep.Some(userId), Rep.Some(title), Rep.Some(sentence), imageUrl, Rep.Some(createdTime), image, Rep.Some(commentId))).shaped.<>({r=>import r._; _1.map(_=> CommentRow.tupled((_1.get, _2.get, _3.get, _4.get, _5, _6.get, _7, _8)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column shop_id SqlType(INT) */
    val shopId: Rep[Int] = column[Int]("shop_id")
    /** Database column user_id SqlType(INT) */
    val userId: Rep[Int] = column[Int]("user_id")
    /** Database column title SqlType(VARCHAR), Length(20,true) */
    val title: Rep[String] = column[String]("title", O.Length(20,varying=true))
    /** Database column sentence SqlType(VARCHAR), Length(500,true) */
    val sentence: Rep[String] = column[String]("sentence", O.Length(500,varying=true))
    /** Database column image_url SqlType(VARCHAR), Length(300,true), Default(None) */
    val imageUrl: Rep[Option[String]] = column[Option[String]]("image_url", O.Length(300,varying=true), O.Default(None))
    /** Database column created_time SqlType(DATETIME) */
    val createdTime: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_time")
    /** Database column image SqlType(MEDIUMBLOB), Default(None) */
    val image: Rep[Option[Array[Byte]]] = column[Option[Array[Byte]]]("image", O.Default(None))
    /** Database column comment_id SqlType(INT), AutoInc, PrimaryKey */
    val commentId: Rep[Int] = column[Int]("comment_id", O.AutoInc, O.PrimaryKey)
  }
  /** Collection-like TableQuery object for table Comment */
  lazy val Comment = new TableQuery(tag => new Comment(tag))

  /** Entity class storing rows of table Genre
   *  @param genreName Database column genre_name SqlType(VARCHAR), Length(10,true)
   *  @param genreId Database column genre_id SqlType(INT), AutoInc, PrimaryKey */
  case class GenreRow(genreName: String, genreId: Option[Int] = None)
  /** GetResult implicit for fetching GenreRow objects using plain SQL queries */
  implicit def GetResultGenreRow(implicit e0: GR[String], e1: GR[Option[Int]]): GR[GenreRow] = GR{
    prs => import prs._
    val r = (<<?[Int], <<[String])
    import r._
    GenreRow.tupled((_2, _1)) // putting AutoInc last
  }
  /** Table description of table genre. Objects of this class serve as prototypes for rows in queries. */
  class Genre(_tableTag: Tag) extends profile.api.Table[GenreRow](_tableTag, Some("demo"), "genre") {
    def * = (genreName, Rep.Some(genreId)).<>(GenreRow.tupled, GenreRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(genreName), Rep.Some(genreId))).shaped.<>({r=>import r._; _1.map(_=> GenreRow.tupled((_1.get, _2)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column genre_name SqlType(VARCHAR), Length(10,true) */
    val genreName: Rep[String] = column[String]("genre_name", O.Length(10,varying=true))
    /** Database column genre_id SqlType(INT), AutoInc, PrimaryKey */
    val genreId: Rep[Int] = column[Int]("genre_id", O.AutoInc, O.PrimaryKey)

    /** Uniqueness Index over (genreName) (database name genre_name) */
    val index1 = index("genre_name", genreName, unique=true)
  }
  /** Collection-like TableQuery object for table Genre */
  lazy val Genre = new TableQuery(tag => new Genre(tag))

  /** Entity class storing rows of table Map
   *  @param latitude Database column latitude SqlType(DOUBLE)
   *  @param longitude Database column longitude SqlType(DOUBLE)
   *  @param mapId Database column map_id SqlType(INT), AutoInc, PrimaryKey */
  case class MapRow(latitude: Double, longitude: Double, mapId: Option[Int] = None)
  /** GetResult implicit for fetching MapRow objects using plain SQL queries */
  implicit def GetResultMapRow(implicit e0: GR[Double], e1: GR[Option[Int]]): GR[MapRow] = GR{
    prs => import prs._
    val r = (<<?[Int], <<[Double], <<[Double])
    import r._
    MapRow.tupled((_2, _3, _1)) // putting AutoInc last
  }
  /** Table description of table map. Objects of this class serve as prototypes for rows in queries. */
  class Map(_tableTag: Tag) extends profile.api.Table[MapRow](_tableTag, Some("demo"), "map") {
    def * = (latitude, longitude, Rep.Some(mapId)).<>(MapRow.tupled, MapRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(latitude), Rep.Some(longitude), Rep.Some(mapId))).shaped.<>({r=>import r._; _1.map(_=> MapRow.tupled((_1.get, _2.get, _3)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column latitude SqlType(DOUBLE) */
    val latitude: Rep[Double] = column[Double]("latitude")
    /** Database column longitude SqlType(DOUBLE) */
    val longitude: Rep[Double] = column[Double]("longitude")
    /** Database column map_id SqlType(INT), AutoInc, PrimaryKey */
    val mapId: Rep[Int] = column[Int]("map_id", O.AutoInc, O.PrimaryKey)
  }
  /** Collection-like TableQuery object for table Map */
  lazy val Map = new TableQuery(tag => new Map(tag))

  /** Entity class storing rows of table Menu
   *  @param menuName Database column menu_name SqlType(VARCHAR), Length(50,true)
   *  @param price Database column price SqlType(INT)
   *  @param shopId Database column shop_id SqlType(INT)
   *  @param menuId Database column menu_id SqlType(INT), AutoInc, PrimaryKey */
  case class MenuRow(menuName: String, price: Int, shopId: Int, menuId: Option[Int] = None)
  /** GetResult implicit for fetching MenuRow objects using plain SQL queries */
  implicit def GetResultMenuRow(implicit e0: GR[String], e1: GR[Int], e2: GR[Option[Int]]): GR[MenuRow] = GR{
    prs => import prs._
    val r = (<<?[Int], <<[String], <<[Int], <<[Int])
    import r._
    MenuRow.tupled((_2, _3, _4, _1)) // putting AutoInc last
  }
  /** Table description of table menu. Objects of this class serve as prototypes for rows in queries. */
  class Menu(_tableTag: Tag) extends profile.api.Table[MenuRow](_tableTag, Some("demo"), "menu") {
    def * = (menuName, price, shopId, Rep.Some(menuId)).<>(MenuRow.tupled, MenuRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(menuName), Rep.Some(price), Rep.Some(shopId), Rep.Some(menuId))).shaped.<>({r=>import r._; _1.map(_=> MenuRow.tupled((_1.get, _2.get, _3.get, _4)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column menu_name SqlType(VARCHAR), Length(50,true) */
    val menuName: Rep[String] = column[String]("menu_name", O.Length(50,varying=true))
    /** Database column price SqlType(INT) */
    val price: Rep[Int] = column[Int]("price")
    /** Database column shop_id SqlType(INT) */
    val shopId: Rep[Int] = column[Int]("shop_id")
    /** Database column menu_id SqlType(INT), AutoInc, PrimaryKey */
    val menuId: Rep[Int] = column[Int]("menu_id", O.AutoInc, O.PrimaryKey)
  }
  /** Collection-like TableQuery object for table Menu */
  lazy val Menu = new TableQuery(tag => new Menu(tag))

  /** Entity class storing rows of table Pricerange
   *  @param minPrice Database column min_price SqlType(INT), Default(None)
   *  @param maxPrice Database column max_price SqlType(INT), Default(None)
   *  @param priceRangeId Database column price_range_id SqlType(INT), AutoInc, PrimaryKey */
  case class PricerangeRow(minPrice: Option[Int] = None, maxPrice: Option[Int] = None, priceRangeId: Option[Int] = None)
  /** GetResult implicit for fetching PricerangeRow objects using plain SQL queries */
  implicit def GetResultPricerangeRow(implicit e0: GR[Option[Int]]): GR[PricerangeRow] = GR{
    prs => import prs._
    val r = (<<?[Int], <<?[Int], <<?[Int])
    import r._
    PricerangeRow.tupled((_2, _3, _1)) // putting AutoInc last
  }
  /** Table description of table priceRange. Objects of this class serve as prototypes for rows in queries. */
  class Pricerange(_tableTag: Tag) extends profile.api.Table[PricerangeRow](_tableTag, Some("demo"), "priceRange") {
    def * = (minPrice, maxPrice, Rep.Some(priceRangeId)).<>(PricerangeRow.tupled, PricerangeRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((minPrice, maxPrice, Rep.Some(priceRangeId))).shaped.<>({r=>import r._; _3.map(_=> PricerangeRow.tupled((_1, _2, _3)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column min_price SqlType(INT), Default(None) */
    val minPrice: Rep[Option[Int]] = column[Option[Int]]("min_price", O.Default(None))
    /** Database column max_price SqlType(INT), Default(None) */
    val maxPrice: Rep[Option[Int]] = column[Option[Int]]("max_price", O.Default(None))
    /** Database column price_range_id SqlType(INT), AutoInc, PrimaryKey */
    val priceRangeId: Rep[Int] = column[Int]("price_range_id", O.AutoInc, O.PrimaryKey)
  }
  /** Collection-like TableQuery object for table Pricerange */
  lazy val Pricerange = new TableQuery(tag => new Pricerange(tag))

  /** Entity class storing rows of table Scene
   *  @param sceneName Database column scene_name SqlType(VARCHAR), Length(10,true)
   *  @param sceneId Database column scene_id SqlType(INT), AutoInc, PrimaryKey */
  case class SceneRow(sceneName: String, sceneId: Option[Int] = None)
  /** GetResult implicit for fetching SceneRow objects using plain SQL queries */
  implicit def GetResultSceneRow(implicit e0: GR[String], e1: GR[Option[Int]]): GR[SceneRow] = GR{
    prs => import prs._
    val r = (<<?[Int], <<[String])
    import r._
    SceneRow.tupled((_2, _1)) // putting AutoInc last
  }
  /** Table description of table scene. Objects of this class serve as prototypes for rows in queries. */
  class Scene(_tableTag: Tag) extends profile.api.Table[SceneRow](_tableTag, Some("demo"), "scene") {
    def * = (sceneName, Rep.Some(sceneId)).<>(SceneRow.tupled, SceneRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(sceneName), Rep.Some(sceneId))).shaped.<>({r=>import r._; _1.map(_=> SceneRow.tupled((_1.get, _2)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column scene_name SqlType(VARCHAR), Length(10,true) */
    val sceneName: Rep[String] = column[String]("scene_name", O.Length(10,varying=true))
    /** Database column scene_id SqlType(INT), AutoInc, PrimaryKey */
    val sceneId: Rep[Int] = column[Int]("scene_id", O.AutoInc, O.PrimaryKey)

    /** Uniqueness Index over (sceneName) (database name scene_name) */
    val index1 = index("scene_name", sceneName, unique=true)
  }
  /** Collection-like TableQuery object for table Scene */
  lazy val Scene = new TableQuery(tag => new Scene(tag))

  /** Entity class storing rows of table Shopimage
   *  @param imagePath Database column image_path SqlType(VARCHAR), Length(300,true)
   *  @param shopId Database column shop_id SqlType(INT)
   *  @param shopImageId Database column shop_image_id SqlType(INT), AutoInc, PrimaryKey */
  case class ShopimageRow(imagePath: String, shopId: Int, shopImageId: Option[Int] = None)
  /** GetResult implicit for fetching ShopimageRow objects using plain SQL queries */
  implicit def GetResultShopimageRow(implicit e0: GR[String], e1: GR[Int], e2: GR[Option[Int]]): GR[ShopimageRow] = GR{
    prs => import prs._
    val r = (<<?[Int], <<[String], <<[Int])
    import r._
    ShopimageRow.tupled((_2, _3, _1)) // putting AutoInc last
  }
  /** Table description of table shopImage. Objects of this class serve as prototypes for rows in queries. */
  class Shopimage(_tableTag: Tag) extends profile.api.Table[ShopimageRow](_tableTag, Some("demo"), "shopImage") {
    def * = (imagePath, shopId, Rep.Some(shopImageId)).<>(ShopimageRow.tupled, ShopimageRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(imagePath), Rep.Some(shopId), Rep.Some(shopImageId))).shaped.<>({r=>import r._; _1.map(_=> ShopimageRow.tupled((_1.get, _2.get, _3)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column image_path SqlType(VARCHAR), Length(300,true) */
    val imagePath: Rep[String] = column[String]("image_path", O.Length(300,varying=true))
    /** Database column shop_id SqlType(INT) */
    val shopId: Rep[Int] = column[Int]("shop_id")
    /** Database column shop_image_id SqlType(INT), AutoInc, PrimaryKey */
    val shopImageId: Rep[Int] = column[Int]("shop_image_id", O.AutoInc, O.PrimaryKey)
  }
  /** Collection-like TableQuery object for table Shopimage */
  lazy val Shopimage = new TableQuery(tag => new Shopimage(tag))

  /** Entity class storing rows of table Shops
   *  @param shopName Database column shop_name SqlType(VARCHAR), Length(50,true)
   *  @param mapId Database column map_id SqlType(INT)
   *  @param genreId Database column genre_id SqlType(INT)
   *  @param sceneId1 Database column scene_id1 SqlType(INT), Default(None)
   *  @param sceneId2 Database column scene_id2 SqlType(INT), Default(None)
   *  @param lunchPriceRangeId Database column lunch_price_range_id SqlType(INT), Default(None)
   *  @param dinnerPriceRangeId Database column dinner_price_range_id SqlType(INT), Default(None)
   *  @param shopAddress Database column shop_address SqlType(VARCHAR), Length(100,true)
   *  @param distance Database column distance SqlType(DOUBLE)
   *  @param createdTime Database column created_time SqlType(DATETIME)
   *  @param updatedTime Database column updated_time SqlType(DATETIME)
   *  @param deleteFlg Database column delete_flg SqlType(BIT), Default(false)
   *  @param image1 Database column image1 SqlType(MEDIUMBLOB), Default(None)
   *  @param image2 Database column image2 SqlType(MEDIUMBLOB), Default(None)
   *  @param image3 Database column image3 SqlType(MEDIUMBLOB), Default(None)
   *  @param shopId Database column shop_id SqlType(INT), AutoInc, PrimaryKey */
  case class ShopsRow(shopName: String, mapId: Int, genreId: Int, sceneId1: Option[Int] = None, sceneId2: Option[Int] = None, lunchPriceRangeId: Option[Int] = None, dinnerPriceRangeId: Option[Int] = None, shopAddress: String, distance: Double, createdTime: java.sql.Timestamp, updatedTime: java.sql.Timestamp, deleteFlg: Boolean = false, image1: Option[Array[Byte]] = None, image2: Option[Array[Byte]] = None, image3: Option[Array[Byte]] = None, shopId: Option[Int] = None)
  /** GetResult implicit for fetching ShopsRow objects using plain SQL queries */
  implicit def GetResultShopsRow(implicit e0: GR[String], e1: GR[Int], e2: GR[Option[Int]], e3: GR[Double], e4: GR[java.sql.Timestamp], e5: GR[Boolean], e6: GR[Option[Array[Byte]]]): GR[ShopsRow] = GR{
    prs => import prs._
    val r = (<<?[Int], <<[String], <<[Int], <<[Int], <<?[Int], <<?[Int], <<?[Int], <<?[Int], <<[String], <<[Double], <<[java.sql.Timestamp], <<[java.sql.Timestamp], <<[Boolean], <<?[Array[Byte]], <<?[Array[Byte]], <<?[Array[Byte]])
    import r._
    ShopsRow.tupled((_2, _3, _4, _5, _6, _7, _8, _9, _10, _11, _12, _13, _14, _15, _16, _1)) // putting AutoInc last
  }
  /** Table description of table shops. Objects of this class serve as prototypes for rows in queries. */
  class Shops(_tableTag: Tag) extends profile.api.Table[ShopsRow](_tableTag, Some("demo"), "shops") {
    def * = (shopName, mapId, genreId, sceneId1, sceneId2, lunchPriceRangeId, dinnerPriceRangeId, shopAddress, distance, createdTime, updatedTime, deleteFlg, image1, image2, image3, Rep.Some(shopId)).<>(ShopsRow.tupled, ShopsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(shopName), Rep.Some(mapId), Rep.Some(genreId), sceneId1, sceneId2, lunchPriceRangeId, dinnerPriceRangeId, Rep.Some(shopAddress), Rep.Some(distance), Rep.Some(createdTime), Rep.Some(updatedTime), Rep.Some(deleteFlg), image1, image2, image3, Rep.Some(shopId))).shaped.<>({r=>import r._; _1.map(_=> ShopsRow.tupled((_1.get, _2.get, _3.get, _4, _5, _6, _7, _8.get, _9.get, _10.get, _11.get, _12.get, _13, _14, _15, _16)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column shop_name SqlType(VARCHAR), Length(50,true) */
    val shopName: Rep[String] = column[String]("shop_name", O.Length(50,varying=true))
    /** Database column map_id SqlType(INT) */
    val mapId: Rep[Int] = column[Int]("map_id")
    /** Database column genre_id SqlType(INT) */
    val genreId: Rep[Int] = column[Int]("genre_id")
    /** Database column scene_id1 SqlType(INT), Default(None) */
    val sceneId1: Rep[Option[Int]] = column[Option[Int]]("scene_id1", O.Default(None))
    /** Database column scene_id2 SqlType(INT), Default(None) */
    val sceneId2: Rep[Option[Int]] = column[Option[Int]]("scene_id2", O.Default(None))
    /** Database column lunch_price_range_id SqlType(INT), Default(None) */
    val lunchPriceRangeId: Rep[Option[Int]] = column[Option[Int]]("lunch_price_range_id", O.Default(None))
    /** Database column dinner_price_range_id SqlType(INT), Default(None) */
    val dinnerPriceRangeId: Rep[Option[Int]] = column[Option[Int]]("dinner_price_range_id", O.Default(None))
    /** Database column shop_address SqlType(VARCHAR), Length(100,true) */
    val shopAddress: Rep[String] = column[String]("shop_address", O.Length(100,varying=true))
    /** Database column distance SqlType(DOUBLE) */
    val distance: Rep[Double] = column[Double]("distance")
    /** Database column created_time SqlType(DATETIME) */
    val createdTime: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_time")
    /** Database column updated_time SqlType(DATETIME) */
    val updatedTime: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_time")
    /** Database column delete_flg SqlType(BIT), Default(false) */
    val deleteFlg: Rep[Boolean] = column[Boolean]("delete_flg", O.Default(false))
    /** Database column image1 SqlType(MEDIUMBLOB), Default(None) */
    val image1: Rep[Option[Array[Byte]]] = column[Option[Array[Byte]]]("image1", O.Default(None))
    /** Database column image2 SqlType(MEDIUMBLOB), Default(None) */
    val image2: Rep[Option[Array[Byte]]] = column[Option[Array[Byte]]]("image2", O.Default(None))
    /** Database column image3 SqlType(MEDIUMBLOB), Default(None) */
    val image3: Rep[Option[Array[Byte]]] = column[Option[Array[Byte]]]("image3", O.Default(None))
    /** Database column shop_id SqlType(INT), AutoInc, PrimaryKey */
    val shopId: Rep[Int] = column[Int]("shop_id", O.AutoInc, O.PrimaryKey)

    /** Uniqueness Index over (shopName) (database name shop_name) */
    val index1 = index("shop_name", shopName, unique=true)
  }
  /** Collection-like TableQuery object for table Shops */
  lazy val Shops = new TableQuery(tag => new Shops(tag))

  /** Entity class storing rows of table Users
   *  @param userName Database column user_name SqlType(VARCHAR), Length(20,true)
   *  @param password Database column password SqlType(VARCHAR), Length(100,true)
   *  @param createdTime Database column created_time SqlType(DATETIME)
   *  @param updatedTime Database column updated_time SqlType(DATETIME)
   *  @param deleteFlg Database column delete_flg SqlType(BIT), Default(false)
   *  @param userId Database column user_id SqlType(INT), AutoInc, PrimaryKey */
  case class UsersRow(userName: String, password: String, createdTime: java.sql.Timestamp, updatedTime: java.sql.Timestamp, deleteFlg: Boolean = false, userId: Option[Int] = None)
  /** GetResult implicit for fetching UsersRow objects using plain SQL queries */
  implicit def GetResultUsersRow(implicit e0: GR[String], e1: GR[java.sql.Timestamp], e2: GR[Boolean], e3: GR[Option[Int]]): GR[UsersRow] = GR{
    prs => import prs._
    val r = (<<?[Int], <<[String], <<[String], <<[java.sql.Timestamp], <<[java.sql.Timestamp], <<[Boolean])
    import r._
    UsersRow.tupled((_2, _3, _4, _5, _6, _1)) // putting AutoInc last
  }
  /** Table description of table users. Objects of this class serve as prototypes for rows in queries. */
  class Users(_tableTag: Tag) extends profile.api.Table[UsersRow](_tableTag, Some("demo"), "users") {
    def * = (userName, password, createdTime, updatedTime, deleteFlg, Rep.Some(userId)).<>(UsersRow.tupled, UsersRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(userName), Rep.Some(password), Rep.Some(createdTime), Rep.Some(updatedTime), Rep.Some(deleteFlg), Rep.Some(userId))).shaped.<>({r=>import r._; _1.map(_=> UsersRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column user_name SqlType(VARCHAR), Length(20,true) */
    val userName: Rep[String] = column[String]("user_name", O.Length(20,varying=true))
    /** Database column password SqlType(VARCHAR), Length(100,true) */
    val password: Rep[String] = column[String]("password", O.Length(100,varying=true))
    /** Database column created_time SqlType(DATETIME) */
    val createdTime: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_time")
    /** Database column updated_time SqlType(DATETIME) */
    val updatedTime: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("updated_time")
    /** Database column delete_flg SqlType(BIT), Default(false) */
    val deleteFlg: Rep[Boolean] = column[Boolean]("delete_flg", O.Default(false))
    /** Database column user_id SqlType(INT), AutoInc, PrimaryKey */
    val userId: Rep[Int] = column[Int]("user_id", O.AutoInc, O.PrimaryKey)

    /** Uniqueness Index over (userName) (database name user_name) */
    val index1 = index("user_name", userName, unique=true)
  }
  /** Collection-like TableQuery object for table Users */
  lazy val Users = new TableQuery(tag => new Users(tag))
}
