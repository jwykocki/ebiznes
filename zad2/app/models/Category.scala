package models

case class Category(id: Int, name: String)

object Category {
  var categories: List[Category] = List(
    Category(1, "Electricity"),
    Category(2, "Home")
  )
}
