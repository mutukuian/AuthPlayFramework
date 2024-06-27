package controllers

import javax.inject._
import play.api.mvc._
import services.AuthService
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class AuthController @Inject()(cc: ControllerComponents, authService: AuthService)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  def showLogin = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.login())
  }

  def showRegister = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.register())
  }

  def login = Action.async(parse.json) { implicit request =>
    val email = (request.body \ "email").as[String]
    val password = (request.body \ "password").as[String]

    authService.login(email, password).map { response =>
      Ok(response)
    }.recover {
      case ex: Exception => InternalServerError(ex.getMessage)
    }
  }

  def register = Action.async(parse.json) { implicit request =>
    val email = (request.body \ "email").as[String]
    val password = (request.body \ "password").as[String]
    val name = (request.body \ "name").as[String]

    authService.register(email, password, name).map { response =>
      Ok(response)
    }.recover {
      case ex: Exception => InternalServerError(ex.getMessage)
    }
  }
}

