package com.jw.kotlinbackend.model


data class CartItem(
    val productId: Long,
    val quantity: Int,
    val price: Double
)

data class Cart(
    val userId: String,
    val items: List<CartItem> = emptyList(),
    val total: Double = 0.0
)

data class AddToCartRequest(
    val productId: Long,
    val quantity: Int
)