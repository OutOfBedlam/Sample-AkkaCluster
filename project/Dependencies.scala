import sbt._
import play.core.PlayVersion

object Dependencies {

  object Versions {
    val scala = "2.12.3"
    val akka = "2.5.6"
  }

  val akka: Seq[ModuleID] = Seq(
    "com.typesafe.akka" %% "akka-actor" % Versions.akka,
    "com.typesafe.akka" %% "akka-remote" % Versions.akka,
    //////////////////////////////////
    // Clustering
    "com.typesafe.akka" %% "akka-cluster" % Versions.akka,
    //"com.typesafe.akka" %% "akka-cluster-metrics" % Versions.akka,
    //"com.typesafe.akka" %% "akka-cluster-sharding" % Versions.akka,
    "com.typesafe.akka" %% "akka-cluster-tools" % Versions.akka,
    //////////////////////////////////
    // Guice
    "com.typesafe.play" %% "play-guice" % PlayVersion.current,
    //////////////////////////////////
    // Logging
    "com.typesafe.akka" %% "akka-slf4j" % Versions.akka,
    "ch.qos.logback" % "logback-classic" % "1.2.3" , // 01-Apr-2017 updated
    "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0",
    //////////////////////////////////
    // Testing
    "com.typesafe.akka" %% "akka-testkit" % Versions.akka % Test
  )

}