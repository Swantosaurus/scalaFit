ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "3.3.1"
val AkkaVersion = "2.9.0"
lazy val root = (project in file("."))
 .settings(
   name := "akkastreams",
   resolvers += "Akka library repository".at("https://repo.akka.io/maven"),
   libraryDependencies ++= Seq(
     "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
     "com.typesafe.akka" %% "akka-stream-testkit" % AkkaVersion % Test
   )
 )

