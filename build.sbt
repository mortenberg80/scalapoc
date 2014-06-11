import com.typesafe.sbt.SbtStartScript

seq(SbtStartScript.startScriptForClassesSettings: _*)

Revolver.settings

organization := "no.iterate"

name := "scala-poc"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.10.4"

scalacOptions += "-deprecation"

libraryDependencies ++= List(
  "net.databinder" %% "unfiltered-directives" % "0.7.1",
  "net.databinder" %% "unfiltered-filter" % "0.7.1",
  "net.databinder" %% "unfiltered-jetty" % "0.7.1",
  "com.typesafe.slick" %% "slick" % "2.0.1",
  "c3p0" % "c3p0" % "0.9.1.2",
  "postgresql" % "postgresql" % "9.1-901.jdbc4",
  "com.h2database" % "h2" % "1.3.166" % "test",
  "joda-time" % "joda-time" % "2.3",
  "org.joda" % "joda-convert" % "1.5",
  "com.github.tototoshi" %% "slick-joda-mapper" % "1.0.1",
  "org.json4s" %% "json4s-native" % "3.2.8",
  "org.json4s" %% "json4s-ext" % "3.2.8",
  "org.slf4j" % "slf4j-simple" % "1.7.7",
  "org.scalatest" %% "scalatest" % "2.1.3" % "test",
  "org.mockito" % "mockito-core" % "1.9.5" % "test"
)
