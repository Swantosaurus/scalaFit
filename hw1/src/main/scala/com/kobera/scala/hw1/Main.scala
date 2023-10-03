package com.kobera.scala.hw01

import com.kobera.scala.asciidrawing.AsciiDrawing
import com.kobera.scala.strings.extensions.*

@main
def main(): Unit = {
  startProgram()
}

def startProgram(): Int = {
  println("hw01 - ascidrawing")

  println("type in what to draw")
  println("to quit input 'q'")
  println("supprotedStructs: triangle, squere, spiral; char you want to print; size")

  val input = scala.io.StdIn.readLine()

  if(input == "q") return 1

  val inputSplit = input.split(";")

  assert(inputSplit.size == 3, "wrong input" )

  val char = inputSplit(1).trim.head
  val size = inputSplit(2).trim.toInt

  println(
    inputSplit(0) match {
      case "squere" => {
        AsciiDrawing.generateSquareFill(size, char)
      }
      case "triangle" => {
        AsciiDrawing.generateTriangle(AsciiDrawing.Pointing.TopLeft, size, char)
      }
      case "spiral" => {
        AsciiDrawing.spiral(size, char)
      }
      case _ => {
        println("unsupported struct") 
      }
    }
    )
  startProgram()
}

