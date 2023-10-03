package com.kobera.scala.asciidrawing.elements

import java.awt.Paint
import java.awt.event.TextEvent
import scala.collection.immutable
import com.kobera.scala
import com.kobera.scala.strings.extensions.*

enum SideAppendFill{
  case Top, Bottom
}

enum VerticalAppendFill{
  case Right, Left
}

class BasicElement(override val content: List[String]) extends TextElement{
  def this(string: String) = this(List(string))

  assert(content.map(_.length).distinct.size <= 1, "All lines must have the same length.")

  infix def above(that: BasicElement, verticalAppendFill: VerticalAppendFill = VerticalAppendFill.Right): BasicElement = {
    val newWidth = this.width max that.width
    val widenThis = verticalAppendFill match {
      case VerticalAppendFill.Right => this.content.map(_.padTo(newWidth, ' '))
      case VerticalAppendFill.Left => this.content.map(_.padLeft(newWidth, ' '))
    }
    val widenThat: List[String] = verticalAppendFill match {
      case VerticalAppendFill.Right => that.content.map(_.padTo(newWidth, ' '))
      case VerticalAppendFill.Left => that.content.map(_.padLeft(newWidth, ' '))
    }

    BasicElement(widenThis ++ widenThat)
  }

  infix def below(that: TextElement, verticalAppendFill: VerticalAppendFill = VerticalAppendFill.Right): BasicElement = {
    val newWidth = this.width max that.width
    val widenThis = verticalAppendFill match {
      case VerticalAppendFill.Right => this.content.map(_.padTo(newWidth, ' '))
      case VerticalAppendFill.Left => this.content.map(_.padLeft(newWidth, ' '))
    }
    val widenThat = verticalAppendFill match {
      case VerticalAppendFill.Right => that.content.map(_.padTo(newWidth, ' '))
      case VerticalAppendFill.Left => that.content.map(_.padLeft(newWidth, ' '))
    }
    BasicElement(widenThat ++ widenThis)
  }

  infix def leftOf(that: TextElement, fillType: SideAppendFill = SideAppendFill.Bottom): BasicElement = {
    val newHeight = this.height max that.height

    val heightenThis: List[String] = fillType match {
      case SideAppendFill.Bottom => this.content ::: List.fill(newHeight - this.height)(" " * this.width)
      case SideAppendFill.Top => List.fill(newHeight - this.height)(" " * this.width) ::: this.content
    }

    val heightenThat: List[String] = fillType match {
      case SideAppendFill.Bottom => that.content ::: List.fill(newHeight - that.height)(" " * that.width)
      case SideAppendFill.Top => List.fill(newHeight - that.height)(" " * that.width) ::: that.content
    }

    BasicElement(
      for ((line1: String, line2: String) <- heightenThis zip heightenThat)
        yield line1 + line2
    )
  }

  def rightOf(
               that: BasicElement,
               fillType: SideAppendFill = SideAppendFill.Bottom
             ): BasicElement = {
    that.leftOf(this)
  }
}


