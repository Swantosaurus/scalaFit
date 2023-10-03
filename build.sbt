ThisBuild / scalaVersion := "3.2.1"
name := "test"

lazy val global = project
  .in(file("."))
  .settings(
    name := "global",
  )
  .aggregate(hw1, strings)

lazy val strings = project
  .in(file("strings"))
  .settings(
    name := "strings",
  )

lazy val hw1 = project
  .in(file("hw1"))
  .settings(
    name := "hw1",
  )
  .dependsOn(asciiDrawing)

lazy val asciiDrawing = project
  .in(file("asciiDrawing"))
  .settings(
    name := "asciiDrawing",
  )
  .dependsOn(strings)
