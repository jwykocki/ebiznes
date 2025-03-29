package controllers

import models.Product
import play.api.mvc._

import javax.inject._
import play.api.libs.json._

@Singleton
class ProductController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  implicit val productFormat = Json.format[Product]

  def getAll = Action {
    Ok(Json.toJson(Product.products))
  }

  def getById(id: Int) = Action {
    Product.products.find(_.id == id) match {
      case Some(product) => Ok(Json.toJson(product))
      case None => NotFound("Product not found")
    }
  }

  def add = Action(parse.json) { request =>
    request.body.validate[Product].map { product =>
      Product.products = Product.products :+ product
      Created(Json.toJson(product))
    }.recoverTotal(_ => BadRequest("Incorrect data"))
  }

  def update(id: Int) = Action(parse.json) { request =>
    request.body.validate[Product].map { newProduct =>
      Product.products = Product.products.map(p => if (p.id == id) newProduct else p)
      Ok(Json.toJson(newProduct))
    }.recoverTotal(_ => BadRequest("Incorrect data"))
  }

  def delete(id: Int) = Action {
    Product.products = Product.products.filterNot(_.id == id)
    NoContent
  }
}