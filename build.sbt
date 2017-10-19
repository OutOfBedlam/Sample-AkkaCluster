import sbt.Keys.{libraryDependencies, logLevel, version}
import com.typesafe.sbt.SbtNativePackager.autoImport.NativePackagerHelper._

version in ThisBuild := "1.0.0"
organization in ThisBuild := "com.uangel"
scalaVersion in ThisBuild := Dependencies.Versions.scala
scalacOptions in ThisBuild += "-feature"

lazy val root = (project in file("."))
  .enablePlugins(JavaAppPackaging)
  .settings(
    name := "SampleAkkaCluster",
    mainClass in Compile := Some("com.uangel.sample.cluster.Main"),
    scalaSource in Compile := baseDirectory(_ / "src/main/scala").value,
    resourceDirectory in Compile := baseDirectory(_ / "src/main/resources").value,
    scalaSource in Test := baseDirectory(_ / "src/test/scala").value,
    resourceDirectory in Test := baseDirectory(_ / "src/test/resources").value,
    libraryDependencies ++= Dependencies.akka,
    resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"
  )