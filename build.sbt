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
  .settings(
    packageName in Universal := s"SampleAkkaCluster-${(version in ThisBuild).value}",
    executableScriptName := "sample",
    scriptClasspath := Seq("${app_home}/../conf") ++ scriptClasspath.value,
    bashScriptConfigLocation := Some("${app_home}/../conf/sample.ini"),
    mappings in Universal ++= directory("bin"),
    mappings in Universal ++= directory("conf"),
    bashScriptExtraDefines ++= Seq(
      """addJava "-DAPP_HOME=$(dirname $app_home)" """,
      """addJava "-Dconfig.file=$(dirname $app_home)/conf/application.conf" """,
      """addJava "-Dlogger.file=$(dirname $app_home)/conf/logback.xml" """,
      """addJava "-Dpidfile.path=$(dirname $app_home)/bin/sample.pid" """,
      """addJava "-Dcom.sun.management.jmxremote" """,
      """addJava "-Dcom.sun.management.jmxremote.port=9091" """,
      """addJava "-Dcom.sun.management.jmxremote.ssl=false" """,
      """addJava "-Dcom.sun.management.jmxremote.authenticate=false" """,
      """addJava "-Dcom.sun.management.jmxremote.local.only=false" """
    )
  )