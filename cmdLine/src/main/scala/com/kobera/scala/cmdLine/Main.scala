import com.kobera.scala.strings.extensions.*

@main 
def main(num: Int)= {
  println(s"num is $num".padLeft(20, 'X'))
  println(s"num is ${num + 1}")
  println(
    List(1, 2, 3).formatted("List: %s")
  )
}

