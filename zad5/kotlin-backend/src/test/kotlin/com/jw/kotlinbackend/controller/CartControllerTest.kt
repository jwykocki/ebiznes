package com.jw.kotlinbackend.controller

import com.jw.kotlinbackend.model.AddToCartRequest
import com.jw.kotlinbackend.model.Cart
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.junit.jupiter.api.Assertions.*

class CartControllerTest {

    private lateinit var cartController: CartController
    private val testSessionId = "test-session-123"

    @BeforeEach
    fun setUp() {
        cartController = CartController()
    }

    @Test
    fun `addToCart should create new cart when session has no cart`() {
        // Given
        val request = AddToCartRequest(productId = 1, quantity = 2)

        // When
        val response = cartController.addToCart(request, testSessionId)

        // Then
        assertEquals(HttpStatus.OK, response.statusCode)
        assertNotNull(response.body)
        assertEquals(1, response.body?.items?.size)
        assertEquals(200.0, response.body?.total)
    }

    @Test
    fun `addToCart should add new item to existing cart`() {
        // Given
        val initialRequest = AddToCartRequest(productId = 1, quantity = 1)
        cartController.addToCart(initialRequest, testSessionId)
        val newRequest = AddToCartRequest(productId = 2, quantity = 3)

        // When
        val response = cartController.addToCart(newRequest, testSessionId)

        // Then
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(2, response.body?.items?.size)
        assertEquals(400.0, response.body?.total)
    }

    @Test
    fun `addToCart should update quantity when adding existing product`() {
        // Given
        val initialRequest = AddToCartRequest(productId = 1, quantity = 1)
        cartController.addToCart(initialRequest, testSessionId)
        val updateRequest = AddToCartRequest(productId = 1, quantity = 2)

        // When
        val response = cartController.addToCart(updateRequest, testSessionId)

        // Then
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(1, response.body?.items?.size)
        assertEquals(3, response.body?.items?.first()?.quantity)
        assertEquals(300.0, response.body?.total)
    }

    @Test
    fun `getCart should return empty cart for new session`() {
        // When
        val response = cartController.getCart("new-session")

        // Then
        assertEquals(HttpStatus.OK, response.statusCode)
        assertTrue(response.body?.items?.isEmpty() ?: false)
        assertEquals(0.0, response.body?.total)
    }

    @Test
    fun `multiple sessions should have separate carts`() {
        // Given
        val session1 = "session-1"
        val session2 = "session-2"
        val request1 = AddToCartRequest(productId = 1, quantity = 1)
        val request2 = AddToCartRequest(productId = 2, quantity = 1)

        // When
        cartController.addToCart(request1, session1)
        cartController.addToCart(request2, session2)

        // Then
        val cart1 = cartController.getCart(session1).body
        val cart2 = cartController.getCart(session2).body

        assertEquals(1, cart1?.items?.size)
        assertEquals(1, cart1?.items?.first()?.productId)
        assertEquals(100.0, cart1?.total)

        assertEquals(1, cart2?.items?.size)
        assertEquals(2, cart2?.items?.first()?.productId)
        assertEquals(100.0, cart2?.total)
    }
}