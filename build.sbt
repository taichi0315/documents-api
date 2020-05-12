import sbt.Keys._
import sbt._

name := """scala-play-auth-api-base-project"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.8"

libraryDependencies += guice

libraryDependencies ++= Seq(
  "net.ixias" %% "ixias"      % "1.1.20",
  "net.ixias" %% "ixias-play" % "1.1.20",
  "mysql"      % "mysql-connector-java" % "5.1.+",
  "net.ruippeixotog" %% "scala-scraper" % "2.2.0",
)
