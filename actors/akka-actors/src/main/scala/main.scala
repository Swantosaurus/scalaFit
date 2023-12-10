import akka.actor.typed.{ActorRef, Behavior, ActorSystem}
import akka.actor.typed.scaladsl.{ActorContext, Behaviors}

//TODO add back comminivation from serve

//TODO mergesort server sends to clients and

object Root {
  def apply(): Behavior[Any] = Behaviors.setup {
    context =>
      context.log.info(s"${context.self.path.name}")
      val server = context.spawn(Server(), "Server")
      context.spawn(Client2(server), "Client2")
      context.spawn(Client1(server), "Client1")
      Behaviors.stopped
  }
}

object Server {
  def apply(): Behavior[Protocol] = Behaviors.setup {
    context => {
      context.log.info(s"${context.self.path.name}")
      Behaviors.receive {
        (context, message: Protocol) => {
          message match {
            case Message(text, ctx) => {
              context.log.info(s"got message: $text from ${ctx.self.path}")
              Behaviors.same
            }
          }
        }
      }
    }
  }
}

object Client2 {
  def apply(server: ActorRef[Protocol]): Behavior[Protocol] = Behaviors.setup {
    context =>
      context.log.info(s"${context.self.path.name}")
      server.tell(Message("test", context))
      server.tell(Message("test1", context))
      Behaviors.stopped
  }
}

object Client1 {
  def apply(server: ActorRef[Protocol]): Behavior[Protocol] = Behaviors.setup {
    context =>
      context.log.info(s"${context.self.path.name}")
      server.tell(Message("test", context))
      server.tell(Message("test1", context))
      server.tell(Message("test2", context))
      Behaviors.stopped
  }
}


trait Protocol
case class Message(text: String, sender: ActorContext[Protocol]) extends Protocol

@main
def main(): Unit = {
  println("Hello world!")
  ActorSystem.create(Root(), "Root")
}

