package com.jw.kotlinbackend.controller

import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.junit.jupiter.api.Assertions.*

class ProductControllerTest {

    private val productController = ProductController()

    @Test
    fun `getAllProducts should return all products`() {
        // When
        val products = productController.getAllProducts()

        // Then
        assertEquals(5, products.size)
        assertEquals("Laptop", products[0].name)
        assertEquals("Headphones", products[4].name)
    }

    @Test
    fun `getProductById should return correct product`() {
        // When
        val product = productController.getProductById(3)

        // Then
        assertNotNull(product)
        assertEquals("Tablet", product?.name)
        assertEquals(999.99, product?.price)
    }

    @Test
    fun `getProductById should return null for non-existent product`() {
        // When
        val product = productController.getProductById(999)

        // Then
        assertNull(product)
    }

    @Test
    fun `products should be in correct order`() {
        // When
        val products = productController.getAllProducts()

        // Then
        assertEquals(1, products[0].id)
        assertEquals(2, products[1].id)
        assertEquals(3, products[2].id)
        assertEquals(4, products[3].id)
        assertEquals(5, products[4].id)
    }

    @Test
    fun `product prices should be positive`() {
        // When
        val products = productController.getAllProducts()

        // Then
        products.forEach { product ->
            assertTrue(product.price > 0)
        }
    }
}