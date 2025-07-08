package com.example.flo.DTO

import io.swagger.v3.oas.annotations.media.Schema
import java.math.BigDecimal

@Schema(description = "Data transfer object for product operations")
data class ProductDto(
    @Schema(description = "Product name", example = "Tabi Socks")
    val name: String,

    @Schema(description = "Product description", example = "Traditional Japanese split-toe socks")
    val description: String,

    @Schema(description = "List of category IDs the product belongs to", example = "[1, 2]")
    val categoryIds: List<Long>,

    @Schema(description = "Product price", example = "1000")
    val price: BigDecimal,

    @Schema(description = "Product status (ACTIVE, INACTIVE, SOLD)", example = "ACTIVE")
    val status: String
)