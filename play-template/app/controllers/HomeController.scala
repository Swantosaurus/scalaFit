package controllers

import model.{User, Users}
import play.api.data.Form
import play.api.data.Forms.{mapping, number, text}
import play.api.mvc._

import javax.inject._

@Singleton
class HomeController @Inject()(cc: ControllerComponents)
  extends AbstractController(cc) with play.api.i18n.I18nSupport {


  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index(userForm))
  }
  def userPost(): Action[AnyContent] = Action {
    implicit request: Request[AnyContent] =>
      val formValidationResult: Form[User] = userForm.bindFromRequest()
      println(s"add: $formValidationResult")
      if(formValidationResult.get.name == "" || formValidationResult.get.age < 0) {
        Redirect(routes.HomeController.index())
      } else  {
        Users.users += (formValidationResult.get.name -> formValidationResult.get)
        Redirect(routes.HomeController.index())
      }
  }

  def deletePost(): Action[AnyContent] = Action {
    implicit request: Request[AnyContent] =>

      val formValidationResult: Form[User] = userForm.bindFromRequest()
      //println(s"delete: $formValidationResult")
      println(s"delete: ${formValidationResult.get.name}")


      Users.users -= formValidationResult.get.name

      Redirect(routes.HomeController.index())
  }


  val userForm: Form[User] = Form[User](
    mapping(
      "name" -> text,
      "age" -> number
    )(User.apply)(User.unapply))
}
