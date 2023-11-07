package model

import scala.collection.mutable
case class User(name: String, age: Int)
object Users {
  val users = mutable.HashMap("Tom" -> User("Tom", 12), "Bob" -> User("Bob", 33))
}