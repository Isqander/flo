package com.example.flo.DTO

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Data transfer object for order operations")
data class OrderDto(
    @Schema(description = "Customer name", example = "Ivan Ivanov")
    val customerName: String,

    @Schema(description = "List of product IDs included in the order", example = "[1, 2]")
    val productIds: List<Long>,

    @Schema(description = "Customer email (optional)", example = "ivan@example.com")
    val email: String? = null,

    @Schema(description = "Customer phone number (optional)", example = "+7 999 123 45 67")
    val phone: String? = null,

    @Schema(description = "Customer Telegram username (optional)", example = "@ivan_ivanov")
    val telegramUsername: String? = null,

    @Schema(description = "Additional comments from customer (optional)", example = "Please deliver as soon as possible")
    val customerComment: String? = null
)
