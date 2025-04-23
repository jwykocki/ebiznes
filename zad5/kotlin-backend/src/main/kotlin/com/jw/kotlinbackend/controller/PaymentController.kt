package com.jw.kotlinbackend.controller

import com.jw.kotlinbackend.model.PaymentRequest
import com.jw.kotlinbackend.model.PaymentResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/payments")
class PaymentController {

    @PostMapping
    fun processPayment(@RequestBody paymentRequest: PaymentRequest): ResponseEntity<PaymentResponse> {
        println("Processing payment for order ${paymentRequest.orderId} with amount ${paymentRequest.amount}")
        val response = PaymentResponse(
            success = true,
            message = "Payment for order ${paymentRequest.orderId} processed successfully",
            transactionId = "TXN-${System.currentTimeMillis()}"
        )
        return ResponseEntity.ok(response)
    }
}