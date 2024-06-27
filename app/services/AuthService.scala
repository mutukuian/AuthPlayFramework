package services

import play.api.libs.json._
import play.api.libs.ws._

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class AuthService @Inject()(ws: WSClient)(implicit ec: ExecutionContext) {
  private val apiUrl = "http://localhost:3001"

  def login(email: String, password: String): Future[JsValue] = {
    val loginData = Json.obj(
      "email" -> email,
      "password" -> password
    )

    ws.url(s"$apiUrl/login")
      .post(loginData)
      .map { response =>
        response.json
      }
  }

  def register(email: String, password: String, name: String): Future[JsValue] = {
    val registerData = Json.obj(
      "email" -> email,
      "password" -> password,
      "name" -> name
    )

    ws.url(s"$apiUrl/register")
      .post(registerData)
      .map { response =>
        response.json
      }
  }
}

