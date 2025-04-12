package com.jw.models

import kotlinx.serialization.Serializable


@Serializable
data class SendMessageRequest(
    val message: String
)
