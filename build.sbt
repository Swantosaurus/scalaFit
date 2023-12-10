ThisBuild / scalaVersion := "3.3.0"
name := "test"

lazy val global = project
  .in(file("."))
  .settings(
    name := "global",
  )
  .aggregate(hw1, cv03, strings, cmdLine, comb,  ui, asciiDrawing, testEnumExtends)

lazy val strings = project
  .in(file("strings"))
  .settings(
    name := "strings",
  )
  
lazy val cv03 = project
  .in(file("cv03"))
  .settings(
    name := "cv03",
  )


lazy val comb = project
  .in(file("cv02_comb"))
  .settings(
    name := "comb",
    libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "2.3.0"
  )

lazy val testEnumExtends = project
    .in(file("testEnumExtends"))
    .settings(
      name := "testEnumExtends",
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
