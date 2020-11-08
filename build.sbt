name := "Scala-GnuPG Webserver"
version := "v0.1.1"
scalaVersion := "2.13.3"

//Akka
val AkkaVersion     = "2.6.8"
val AkkaHttpVersion = "10.2.1"
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-stream"      % AkkaVersion,
  "com.typesafe.akka" %% "akka-http"        % AkkaHttpVersion
)

//Config Factory
libraryDependencies += "com.typesafe" % "config" % "1.4.0"

//Logger
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"

//Better Files
libraryDependencies += "com.github.pathikrit" %% "better-files" % "3.9.1"

lazy val root = (project in file("."))
  .enablePlugins(BuildInfoPlugin)
  .enablePlugins(DockerPlugin, JavaAppPackaging)
  .settings(
    //SBT Build Info
    buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),
    buildInfoPackage := "de.maxbundscherer.gnupg.utils",
    //Docker
    dockerBaseImage := "openjdk:11-jre"
    //dockerExposedVolumes := Seq("/opt/docker/workDir") - maybe not needed
  )
