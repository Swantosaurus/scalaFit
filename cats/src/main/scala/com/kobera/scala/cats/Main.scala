import cats.data.State
import scala.collection.mutable
type Out = Int
type Stack = List[Out]
type Instr = State[Stack, Out]


def  None() = State[Stack, Out] {
  (stack: Stack) => (List(), 0)
}

@main
def main() = {
  val res = operand(3).run(Nil)
  println(res.value)
  println("Hell world!")

  println(program)

  val op1 = operand(5).run(operand(6))
  println(op1)

 // val scanned = scanner("3 4 +")
  
 // println(scanned)

  //val prog = for(scan <- scanned) yield {println(scan)}

}


/*
def scanner(s: String) = {
  s.split(" ").foldLeft(None()) {
    (acc, current) => current match
      case "+" => binOper(_ + _)
      case "-" => binOper(_ - _)
      case "*" => binOper(_ * _)
      case "/" => binOper(_ / _)
      case _ => operand(current.toInt)
  }
}
*/

def operand(n: Int) = State[Stack, Out] {
  (stack: Stack) => (n :: stack, n)
}

def binOper(function : (Out, Out) => Out) = State[Stack, Out] {
  (stack: Stack) => stack match {
    case a :: b :: tail => (function(a, b) :: tail, function(a, b))
    case _ => sys.error("Stack underflow")
  }
}



def program = for {
  r1 <- operand(3)
  r2 <- operand(4)
  r3 <- binOper(_ + _)
} yield r3 



