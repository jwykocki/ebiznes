package controllers

import models.Category
import play.api.mvc._
import play.api.libs.json._

import javax.inject._

@Singleton
class CategoryController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  implicit val categoryFormat = Json.format[Category]

  def getAll = Action {
    Ok(Json.toJson(Category.categories))
  }

  def getById(id: Int) = Action {
    Category.categories.find(_.id == id) match {
      case Some(category) => Ok(Json.toJson(category))
      case None => NotFound("Category not found")
    }
  }

  def add = Action(parse.json) { request =>
    request.body.validate[Category].map { category =>
      Category.categories = Category.categories :+ category
      Created(Json.toJson(category))
    }.recoverTotal(_ => BadRequest("Incorrect data"))
  }

  def update(id: Int) = Action(parse.json) { request =>
    request.body.validate[Category].map { newCategory =>
      Category.categories = Category.categories.map(c => if (c.id == id) newCategory else c)
      Ok(Json.toJson(newCategory))
    }.recoverTotal(_ => BadRequest("Incorrect data"))
  }

  def delete(id: Int) = Action {
    Category.categories = Category.categories.filterNot(_.id == id)
    NoContent
  }
}
