name := """genetic_algorithm"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.13.1"

libraryDependencies += guice
// https://mvnrepository.com/artifact/org.apache.poi/poi
libraryDependencies += "org.apache.poi" % "poi-ooxml" % "4.1.2"

// https://mvnrepository.com/artifact/net.sf.jgap/jgap
libraryDependencies += "net.sf.jgap" % "jgap" % "3.4.4"

