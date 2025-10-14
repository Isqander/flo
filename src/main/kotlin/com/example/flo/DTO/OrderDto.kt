package com.example.flo.DTO

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

@Schema(description = "Data transfer object for order operations")
data class OrderDto(
    @field:NotBlank(message = "Customer name is required")
    @field:Size(min = 2, max = 100, message = "Customer name must be between 2 and 100 characters")
    @Schema(description = "Customer name", example = "Ivan Ivanov")
    val customerName: String,

    @field:NotEmpty(message = "At least one product must be selected")
    @Schema(description = "List of product IDs included in the order", example = "[1, 2]")
    val productIds: List<Long>,

    @field:Email(message = "Email must be valid")
    @Schema(description = "Customer email (optional)", example = "ivan@example.com")
    val email: String? = null,

    @field:Size(max = 20, message = "Phone number must not exceed 20 characters")
    @Schema(description = "Customer phone number (optional)", example = "+7 999 123 45 67")
    val phone: String? = null,

    @field:Size(max = 50, message = "Telegram username must not exceed 50 characters")
    @Schema(description = "Customer Telegram username (optional)", example = "@ivan_ivanov")
    val telegramUsername: String? = null,

    @field:Size(max = 1000, message = "Comment must not exceed 1000 characters")
    @Schema(description = "Additional comments from customer (optional)", example = "Please deliver as soon as possible")
    val customerComment: String? = null
)
