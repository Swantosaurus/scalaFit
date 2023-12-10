import akka.actor.typed.{ActorRef, Behavior, ActorSystem}
import akka.actor.typed.scaladsl.{ActorContext, Behaviors}


object  MergeServer {
    def apply(data: List[Int]): Behavior[Any] = {
        Behaviors.setup { context =>
            val mergeActor = context.spawn(MergeActor(), "mergeActor")
        }    
    }
}


class MergeActor {

    var id = -1

    def apply(parent: ActorRef[Protocol]): Behavior[Any] = {
        Behaviors.receive { (context, message) =>
            //split the list into two and send them to the two merge actors
            message match {
                case Init(list) => {
                }
                case Data(list, parentId, isSorted) =>
                    if(!isSorted) {
                        id = parentId
                        val (left, right) = list.splitAt(list.length / 2)
                            val leftMergeActor = context.spawn(MergeActor(), "leftMergeActor")
                            val rightMergeActor = context.spawn(MergeActor(), "rightMergeActor")
                            leftMergeActor ! Data(left, parentId + 1, false)
                            rightMergeActor ! Data(right, parentId + 1, false)
                            Behaviors.same
                    }
                    else {
                        //send the sorted list to the parent
                        parent ! Data(list, parentId, true)
                        Behaviors.same
                    }
                case _ => Behaviors.same

            }
        }
    }



}


trait ServerMessage

case class Data(list: List[Int], parentId: Int, isSorted: Boolean) extends ServerMessage

case class Init(list: List[Int]) extends ServerMessage
