package com.kobera.scala.asciidrawing

import java.awt.Paint
import java.awt.event.TextEvent
import scala.collection.immutable
import com.kobera.scala.asciidrawing.elements.VerticalAppendFill.*
import com.kobera.scala.asciidrawing.elements.*

object AsciiDrawing {
  def generateTriangle(base: Pointing, height: Int, char : Char): BasicElement = {
    assert(height > 0, "Height must be greater than 0")
    var elem : BasicElement = EmptyElement
    base match {
      case Pointing.TopLeft => 1 to height foreach { (it: Int) =>
        elem = elem above BasicElement(char.toString * it)
      }
      case Pointing.BottomLeft => 1 to height foreach { (it: Int) =>
        elem = elem below BasicElement(char.toString * it)
      }
      case Pointing.TopRight => 1 to height foreach { (it: Int) =>
        elem = elem.above(BasicElement(char.toString * it), VerticalAppendFill.Left)
      }
      case Pointing.BottomRight => 1 to height foreach { (it: Int) =>
        elem = elem.below(BasicElement(char.toString * it), verticalAppendFill = VerticalAppendFill.Left)//elem.above(BasicElement(char.toString * it), verticalAppendFill = VerticalAppendFill.Left)
      }
      case _ => throw Exception("Not implemented")
    }
    elem
  }
  enum Pointing {
    case BottomLeft, TopLeft, BottomRight, TopRight
  }

  def generateSquareEmpty(height: Int, char: Char): TextElement = {

    val halfHeight = height / 2

    (generateTriangle(Pointing.BottomLeft, halfHeight, char)
      leftOf  generateTriangle(Pointing.BottomRight, halfHeight, char))
        .above (generateTriangle(Pointing.TopLeft, halfHeight, char)
          leftOf generateTriangle(Pointing.TopRight, halfHeight, char))
  }

  def generateSquareFill(height: Int, char: Char): TextElement = {
    val halfHeight = height / 2

    (generateTriangle(Pointing.TopRight, halfHeight, char)
      leftOf generateTriangle(Pointing.TopLeft, halfHeight, char))
        .above (generateTriangle(Pointing.BottomRight, halfHeight, char)
          leftOf generateTriangle(Pointing.BottomLeft, halfHeight, char))
  }

  object Left {
    def unapply(x: Int) = if (x % 4 == 0) Some(x) else None
  }

  object Top {
    def unapply(x: Int) =  if (x % 4 == 1) Some(x) else None
  }

  object Right {
    def unapply(x: Int) =  if (x % 4 == 2) Some(x) else None
  }

  object Bottom {
    def unapply(x: Int) =  if (x % 4 == 3) Some(x) else None
  }


  def spiral(repeats: Int, char: Char): BasicElement = {
    if(repeats == 1) return BasicElement(char.toString)
    repeats match {
      case Left(repeats) => BasicElement(List.fill(repeats) { char.toString }) leftOf(spiral(repeats -1, char), SideAppendFill.Top)
      case Top(repeats) => BasicElement(char.toString * repeats) above spiral(repeats -1, char)
      case Right(repeats) => BasicElement(List.fill(repeats) { char.toString }) rightOf spiral(repeats -1, char)
      case Bottom(repeats) => BasicElement(char.toString * repeats) below(spiral(repeats -1, char), VerticalAppendFill.Left)
    }
  }
}
