package com.jw.kotlinbackend.controller

import com.jw.kotlinbackend.model.Product
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/products")
class ProductController {

    private val products = listOf(
        Product(1, "Laptop", 2999.99, "High performance laptop"),
        Product(2, "Smartphone", 1499.99, "Latest model smartphone"),
        Product(3, "Tablet", 999.99, "Portable tablet device"),
        Product(4, "Smartwatch", 799.99, "Fitness tracker and smartwatch"),
        Product(5, "Headphones", 399.99, "Noise cancelling headphones")
    )

    @GetMapping
    fun getAllProducts(): List<Product> = products

    @GetMapping("/{id}")
    fun getProductById(@PathVariable id: Long): Product? =
        products.find { it.id == id }
}