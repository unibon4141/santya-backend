
name := """santya-backend"""
organization := "com.example"

version := "1.0-SNAPSHOT"
val circeVersion = "0.14.1"

//コンパイラプラグインを有効化
autoCompilerPlugins := true

lazy val root = (project in file(".")).enablePlugins(PlayScala)
  .settings(
    scalaVersion := "2.13.12",
    libraryDependencies += guice,
    libraryDependencies += jdbc,
    libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "6.0.0-RC2" % Test,
    libraryDependencies += "mysql" % "mysql-connector-java" % "8.0.30",
    libraryDependencies ++= Seq(
      "io.circe" %% "circe-core",
      "io.circe" %% "circe-generic",
      "io.circe" %% "circe-parser"
    ).map(_ % circeVersion),
    //    cicerをplayで使うために必要なライブラリ
    libraryDependencies += "com.dripower" %% "play-circe" % "2814.1",
    //    base64エンコード用
    libraryDependencies += "commons-codec" % "commons-codec" % "1.16.0",
    libraryDependencies += "com.github.jwt-scala" %% "jwt-circe" % "10.0.0",
    //    Slick
    libraryDependencies += "com.typesafe.play" %% "play-slick" % "5.1.0",
    libraryDependencies += "com.typesafe.slick" %% "slick-codegen" % "3.4.1",

//    GCS
    libraryDependencies += "com.google.cloud" % "google-cloud-storage" % "2.35.0"
  )