package com.jw.kotlinbackend.controller

import com.jw.kotlinbackend.model.AddToCartRequest
import com.jw.kotlinbackend.model.Cart
import com.jw.kotlinbackend.model.CartItem
import com.jw.kotlinbackend.model.Product
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/cart")
class CartController {

    private val carts = mutableMapOf<String, Cart>()

    @PostMapping("/add")
    fun addToCart(
        @RequestBody request: AddToCartRequest,
        @RequestHeader("X-Session-Id") sessionId: String
    ): ResponseEntity<Cart> {

        val product = Product(request.productId, "Sample Product", 100.0)
        
        val currentCart = carts[sessionId] ?: Cart(sessionId)
        val existingItem = currentCart.items.find { it.productId == request.productId }
        
        val updatedItems = if (existingItem != null) {
            currentCart.items.map {
                if (it.productId == request.productId) it.copy(quantity = it.quantity + request.quantity)
                else it
            }
        } else {
            currentCart.items + CartItem(
                productId = product.id,
                quantity = request.quantity,
                price = product.price
            )
        }
        
        val updatedCart = currentCart.copy(
            items = updatedItems,
            total = updatedItems.sumOf { it.price * it.quantity }
        )
        
        carts[sessionId] = updatedCart
        println("Updated cart for session $sessionId: $updatedCart")
        return ResponseEntity.ok(updatedCart)
    }

    @GetMapping
    fun getCart(@RequestHeader("X-Session-Id") sessionId: String): ResponseEntity<Cart> {
        return ResponseEntity.ok(carts[sessionId] ?: Cart(sessionId))
    }
}