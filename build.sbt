name := "scala-webserver-GnuPG"
version := "0.1"
scalaVersion := "2.13.3"

val AkkaVersion = "2.6.8"
val AkkaHttpVersion = "10.2.1"
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion
)

//Config Factory
libraryDependencies += "com.typesafe" % "config" % "1.4.0"

//Logger
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"