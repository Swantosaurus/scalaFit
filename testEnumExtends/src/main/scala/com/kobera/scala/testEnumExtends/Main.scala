package com.kobera.scala.testEnumExtends


@main
def run(response: Int): Unit = {
  ServerResponse.unapply(response) match {
    case SuccessResponse => println("Ok")
    case ErrorServerResponse => println("Error: ")
  }
}

class ServerResponse {
  def unapply(code: Int): Option[SercerResponse] = if(code <= 299) SuccessRespoe.unapply(code) else ErrorServerResponse(code) 
}

enum SuccessResponse(code: Int) extends ServerResponse {
  case Ok extends SuccessResponse(200)
  case Created extends SuccessResponse(201)
  case Accepted extends SuccessResponse(202)

  override def unapply(code: Int): Option[SuccessResponse] = { 
    if(code <= 299) SuccessResponse(code) else None 
  }
}

enum ErrorServerResponse(code: Int, message: String) extends ServerResponse() {
  case NotFound extends ErrorServerResponse(404, "Not found")
  case BadRequest extends ErrorServerResponse(400, "Bad request")
  
  def this(code: Int) = {
    if(code >= 400) Some(code) else None
  }
}
