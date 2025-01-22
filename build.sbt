ThisBuild / scalaVersion := "3.6.3"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.chriskimberley.json"
ThisBuild / organizationName := "chriskimberley"

lazy val root = (project in file("."))
  .settings(
    name := "skylight",
    libraryDependencies ++= Seq(
      "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5",
      "ch.qos.logback" % "logback-classic" % "1.5.6",
      "org.scalatest" %% "scalatest-flatspec" % "3.2.19" % Test
    )
  )
