package com.example.flo.DTO

import java.math.BigDecimal

data class ProductDto(
    val name: String,
    val description: String,
    val categoryIds: List<Long>,
    val price: BigDecimal,
    val status: String
)