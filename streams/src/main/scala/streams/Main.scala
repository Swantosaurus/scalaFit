import akka.actor.*
import akka.NotUsed
import scala.concurrent.*
import scala.concurrent.duration.*
import akka.stream.scaladsl.*
import akka.Done
import scala.runtime.Statics

given as: ActorSystem = ActorSystem("forstreams")

extension (source: Source[Double, NotUsed]){
  def avrg: Double = {
    val avrg: Flow[Double, Double, NotUsed] = Flow[Double].fold((0.0, 0))(
      (acc, elem) => 
        (acc._1 + elem, acc._2 + 1)
      ).map { case (sum, count) => sum.toDouble / count }
    val avrgRes = source.via(avrg).runWith(Sink.head)
    Await.result(avrgRes, 1.second)
  }
  def mse(average: Double): Double = {
    val mseFlow: Flow[Double, Double, NotUsed] = Flow[Double].fold((0.0, 0))(
      (acc, elem) => (acc._1 + Math.pow(elem - average, 2), acc._2 + 1)
    ).map { case (sumSq, count) => sumSq / count }
    val mse = source.via(mseFlow).runWith(Sink.head)
    Await.result(mse, 1.second)
  }
}

def tests(): Unit = {
  val testCases = Seq(
    (Source(List(1.0, 2.0, 3.0, 4.0, 5.0)), 3.0, 2.0), // Average: 3.0, MSE: 2.0
    (Source(List(10.0, 10.0, 10.0, 10.0, 10.0)), 10.0, 0.0), // Average: 10.0, MSE: 0.0
    (Source(List(1.0, 1.0, 1.0, 1.0, 1.0)), 1.0, 0.0), // Average: 1.0, MSE: 0.0
    (Source(List(5.0, 1.0, 9.0, -3.0)), 3.0, 20.0) // Average: 3.0, MSE: 20.0
  )

  testCases.foreach { case (source, expectedAvg, expectedMse) =>
    val avg = source.avrg
    val mse = source.mse(avg)
    println(s"Test Case - Expected Avg: $expectedAvg, Actual Avg: $avg, Expected MSE: $expectedMse, Actual MSE: $mse")
  }
}

@main 
def main(): Unit = {
  tests()
}
