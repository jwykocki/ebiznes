package models

case class Product(id: Int, name: String, price: Double, categoryId: Int)

object Product {
  var products: List[Product] = List(
    Product(1, "TV", 3000.0, 1),
    Product(2, "Notebook", 1500.0, 1)
  )
}
