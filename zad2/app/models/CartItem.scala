package models

case class CartItem(productId: Int, quantity: Int)

object Cart {
  var cart: List[CartItem] = List()
}
