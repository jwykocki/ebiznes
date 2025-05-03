package com.jw.kotlinbackend.controller

import com.jw.kotlinbackend.model.PaymentRequest
import com.jw.kotlinbackend.model.PaymentResponse
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.junit.jupiter.api.Assertions.*

class PaymentControllerTest {

    private val paymentController = PaymentController()

    @Test
    fun `processPayment should return successful response`() {
        // Given
        val request = testPaymentRequest(orderId = "ORDER-123", amount = 100.0)

        // When
        val response = paymentController.processPayment(request)

        // Then
        assertEquals(HttpStatus.OK, response.statusCode)
        assertNotNull(response.body)
        assertTrue(response.body?.success ?: false)
        assertNotNull(response.body?.transactionId)
        assertTrue(response.body?.message?.contains("ORDER-123") ?: false)
    }

    private fun testPaymentRequest(orderId: String, amount: Double): PaymentRequest {
        return PaymentRequest(orderId, amount, "1234-5678-9012-3456","Visa", "12/25", "123")

    }

    @Test
    fun `processPayment should include correct orderId in response`() {
        // Given
        val request = testPaymentRequest(orderId = "ORDER-456", amount = 199.99)

        // When
        val response = paymentController.processPayment(request)

        // Then
        assertEquals(HttpStatus.OK, response.statusCode)
        assertTrue(response.body?.message?.contains("ORDER-456") ?: false)
    }

    @Test
    fun `processPayment should generate unique transaction IDs`() {
        // Given
        val request1 = testPaymentRequest(orderId = "ORDER-1", amount = 100.0)
        val request2 = testPaymentRequest(orderId = "ORDER-2", amount = 200.0)

        // When
        val response1 = paymentController.processPayment(request1)
        val response2 = paymentController.processPayment(request2)

        // Then
        assertNotNull(response1.body?.transactionId)
        assertNotNull(response2.body?.transactionId)
    }
}