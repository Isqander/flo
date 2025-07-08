package com.example.flo.DTO

data class OrderDto(
    val customerName: String,
    val productIds: List<Long>,
    val email: String? = null,
    val phone: String? = null,
    val telegramUsername: String? = null,
    val customerComment: String? = null
)
