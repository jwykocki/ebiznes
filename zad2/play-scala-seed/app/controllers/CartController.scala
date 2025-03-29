package controllers

import models.{Cart, CartItem}
import play.api.mvc._
import play.api.libs.json._

import javax.inject._

@Singleton
class CartController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  implicit val cartItemFormat = Json.format[CartItem]

  def getCart = Action {
    Ok(Json.toJson(Cart.cart))
  }

  def addToCart = Action(parse.json) { request =>
    request.body.validate[CartItem].map { item =>
      Cart.cart = Cart.cart :+ item
      Created(Json.toJson(item))
    }.recoverTotal(_ => BadRequest("Incorrect data"))
  }

  def removeFromCart(productId: Int) = Action {
    Cart.cart = Cart.cart.filterNot(_.productId == productId)
    NoContent
  }

  def clearCart = Action {
    Cart.cart = List()
    NoContent
  }
}