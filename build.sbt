name := """play-java-seed"""
organization := "com.playlearn"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.13.14"

// Add the necessary dependencies
libraryDependencies +=guice

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-json" % "2.10.5",
  "com.typesafe.play" %% "play-ws" % "2.9.4"
)

libraryDependencies += ws


