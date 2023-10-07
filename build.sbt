ThisBuild / scalaVersion := "3.3.0"
name := "test"

lazy val global = project
  .in(file("."))
  .settings(
    name := "global",
  )
  .aggregate(hw1, strings, cmdLine, ui, asciiDrawing)

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

lazy val cmdLine = project
  .in(file("cmdLine"))
  .settings(
    name := "cmdLine",
  )
  .dependsOn(strings)

lazy val ui = project
  .in(file("ui"))
  .settings(
    name := "ui",
  )

lazy val asciiDrawing = project
  .in(file("asciiDrawing"))
  .settings(
    name := "asciiDrawing",
  )
  .dependsOn(strings)
