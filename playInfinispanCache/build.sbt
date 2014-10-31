organization := "io.github.n4cer"

name := "playInfinispanCache"

version := "0.0.1"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

val infinispan = "org.infinispan" % "infinispan-core" % "6.0.2.Final"

libraryDependencies ++= Seq(
  cache,
  infinispan
)
