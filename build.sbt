import play.sbt.PlayImport.*

name := "play-java-seed"
organization := "com.play learn"
version := "1.0-SNAPSHOT"

lazy val root = (project in file("."))
  .enablePlugins(PlayJava,PlayEbean)

scalaVersion := "2.13.14"
playEbeanVersion := "8.2.0"

libraryDependencies ++= Seq(
  guice,
  ehcache,
  "com.mysql" % "mysql-connector-j" % "8.4.0",
  javaJdbc,
  jdbc,
  javaWs,
  "ch.qos.logback" % "logback-classic" % "1.5.6",
  "ch.qos.logback" % "logback-core" % "1.5.6",
  "ch.qos.logback" % "logback-access" % "1.4.14",
  "org.springframework.security" % "spring-security-crypto" % "6.3.0"
)

Compile / playEbeanModels := Seq("models.*")
