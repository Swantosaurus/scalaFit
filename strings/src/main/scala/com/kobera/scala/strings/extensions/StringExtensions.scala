package com.kobera.scala.strings.extensions

extension (str: String) {
  def padLeft(width: Int, char: Char): String = {
    assert(width >= str.length, "width has to be greater than current width")
    " " * (width - str.length) + str
  }
}
