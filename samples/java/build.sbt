name := """playInfinispanCache-java-sample"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

val infinispan = "org.infinispan" % "infinispan-core" % "6.0.2.Final"
val playInfinispanCache = "io.github.n4cer" % "playinfinispancache_2.11" % "0.0.1"

libraryDependencies ++= Seq(
  cache,
  infinispan,
  playInfinispanCache
)

resolvers += Resolver.url("n4cer's Repository", url("https://n4cer.github.io/releases/"))(Resolver.ivyStylePatterns)
