package com.kobera.scala.comb

import com.kobera.scala.comb.ExprParser.parseAll
import com.kobera.scala.comb.Operator.*

import scala.util.parsing.combinator.*


@main
def man(): Unit = {
   val parserRes = ExprParser.parseAll(ExprParser.chainUniversalParser, "1.0 * 2.0 * 3")
   test(parserRes.get)

   val parseRes2 = ExprParser.parseAll(ExprParser.chainUniversalParser, "1.0 + 2.0 + 3")
   test(parseRes2.get)

   val parserRes3 = ExprParser.parseAll(ExprParser.chainUniversalParser, "1.0 * 2.0 * 3")
   val parserRes4 = ExprParser.parseAll(ExprParser.chainUniversalParser, "1.0 + 2.0 + 3")

   test(parserRes3.get)
   test(parserRes4.get)

  val addMultParser1 = ExprParser.parseAll(ExprParser.chainUniversalParser, "1 + 2 * 3")
  val addMultParser2 = ExprParser.parseAll(ExprParser.chainUniversalParser, "1 + 3")
  val addMultParser3 = ExprParser.parseAll(ExprParser.chainUniversalParser, "1 * 3")

  val addMultParser4 = ExprParser.parseAll(ExprParser.chainUniversalParser, "1 * 2 + 3")
  val MultAddMult = ExprParser.parseAll(ExprParser.chainUniversalParser, "1 + 2 * 3 + 4")
  val MultAddMult2 = ExprParser.parseAll(ExprParser.chainUniversalParser, "1 * 2 + 3 * 4")


  test(addMultParser1.get)
  test(addMultParser2.get)
  test(addMultParser3.get)
  test(addMultParser4.get)
  test(addMultParser2.get)
  test(MultAddMult.get)
  test(MultAddMult2.get)

  /** edge cases */
  test(ExprParser.parseAll(ExprParser.chainUniversalParser, "0").get)
  try {
    test(ExprParser.parseAll(ExprParser.chainUniversalParser, "").get)
    throw IllegalStateException("test of empty string")
  } catch {
    case e: RuntimeException => {}
  }

  try {
    test(ExprParser.parseAll(ExprParser.chainUniversalParser, "+").get)
    throw IllegalStateException("test of empty string")
  } catch {
    case e: RuntimeException => {}
  }

  try {
    test(ExprParser.parseAll(ExprParser.chainUniversalParser, "0 *").get)
    throw IllegalStateException("test of empty string")
  } catch {
    case e: RuntimeException => {}
  }

  try {
    test(ExprParser.parseAll(ExprParser.chainUniversalParser, "0 +- 2").get)
    throw IllegalStateException("test of empty string")
  } catch {
    case e: RuntimeException => {}
  }

  test(ExprParser.parseAll(ExprParser.chainUniversalParser, "-1 - -2").get)



}



def test(expr: Expr): Unit = {
  println(s"${expr.txt} = ${expr.value}")
}

object ExprParser extends JavaTokenParsers {
  //def anyMatch = rep(multiplicationPattern | additionPattern | digitPattern)
  private val multiplicationPattern = ("*" | "/")
  private val additionPattern = ("+" | "-")
  private val operationPattern = (multiplicationPattern | additionPattern)
  private val digitPattern = ("0" | "1" | "2" | "3" | "4"| "5" | "6" | "7" | "8" | "9" | ".")

  lazy val litParser: Parser[Expr] = floatingPointNumber ^^ { s => Literal(s.toDouble) }
  lazy val MultParser: Parser[Expr] = (litParser) ~ rep(multiplicationPattern ~ (litParser)) ^^ {
    res =>
      res match {
        case e ~ rest => {
          rest.foldLeft(e) { (previous, rest) =>
            BinOp(Operator.valueOf(rest._1), previous, rest._2)
          }
        }
      }
  }

  lazy val AddParser: Parser[Expr] = (litParser) ~ rep(additionPattern ~ litParser) ^^ {
    res =>
      res match {
        case e ~ rest => {
          rest.foldLeft(e) { (previous, rest) =>
            BinOp(Operator.valueOf(rest._1), previous, rest._2)
          }
        }
      }
  }

  lazy val AddMultParser: Parser[Expr] = (additionPattern | digitPattern) ~ additionPattern ~ MultParser  ^^ {
    res =>{
      res match {
        case addition ~ lastAdd ~ mult => {
          val addExpr = parseAll(AddParser, addition).get
          Literal(0)
          BinOp(Operator.valueOf(lastAdd), addExpr, mult)
        }
      }
    }
  }

  lazy val universalParser : Parser[Expr] = (AddMultParser ||| MultParser ||| litParser)

  lazy val chainUniversalParser : Parser[Expr] = universalParser ~ rep((additionPattern) ~ universalParser) ^^ {
    res => res match {
      case first ~ rest => {
        rest.foldLeft(first){ (prev, res) =>
          BinOp(Operator.valueOf(res._1), prev, res._2)
        }
      }
    }
  }


  lazy val MultAddParser: Parser[Expr] = MultParser ~ rep(additionPattern ~ AddParser) ^^ {
    res => res match
      case mult ~ rest => {
        rest.foldLeft(mult) { (prev, rest) =>
          BinOp(Operator.valueOf(rest._1), prev, rest._2)
        }
      }
  }
}

enum Operator {
  case +, -, *, /
}

trait Expr {
  lazy val value: Double
  lazy val txt: String
}

class Literal(_value: Double) extends Expr {
  override lazy val value: Double = _value
  override lazy val txt: String = value.toString
}

case class BinOp(op: Operator, l: Expr, r: Expr) extends Expr {

  import Operator.*

  override lazy val value: Double = op match {
    case + => l.value + r.value
    case - => l.value - r.value
    case * => l.value * r.value
    case / => l.value / r.value
  }
  override lazy val txt: String = s"${l.txt} ${op.toString} ${r.txt}"
}

case object One extends Literal(1.0)
