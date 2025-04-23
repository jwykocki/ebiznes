package com.jw.kotlinbackend.model

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive

data class PaymentRequest(
    @field:NotBlank
    val orderId: String,
    
    @field:Positive
    val amount: Double,
    
    @field:NotBlank
    val cardNumber: String,
    
    @field:NotBlank
    val cardHolder: String,
    
    @field:NotBlank
    val expiryDate: String,
    
    @field:NotBlank
    val cvv: String
)

data class PaymentResponse(
    val success: Boolean,
    val message: String,
    val transactionId: String
)