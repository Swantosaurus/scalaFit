package com.kobera.scala.asciidrawing.elements

trait TextElement{
  val content: List[String]
  lazy val width: Int = if(content.isEmpty) 0 else content.head.length
  val height: Int = content.size

  override def toString: String = content.mkString("\n")
}
