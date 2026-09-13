package com.example.flo.DTO

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import java.math.BigDecimal

@Schema(description = "Data transfer object for product operations")
data class ProductDto(
    @Schema(description = "Product name", example = "Tabi Socks")
    val name: String,

    @Schema(description = "English product name (optional; falls back to the legacy name)")
    val nameEn: String? = null,

    @Schema(description = "Russian product name (optional; falls back to English)")
    val nameRu: String? = null,

    @Schema(description = "Chinese product name (optional; falls back to English)")
    val nameZh: String? = null,

    @Schema(description = "Spanish product name (optional; falls back to English)")
    val nameEs: String? = null,

    @Schema(description = "Georgian product name (optional; falls back to English)")
    val nameKa: String? = null,

    @Schema(description = "Product description", example = "Traditional Japanese split-toe socks")
    val description: String,

    @Schema(description = "English product description (optional; falls back to the legacy description)")
    val descriptionEn: String? = null,

    @Schema(description = "Russian product description (optional; falls back to English)")
    val descriptionRu: String? = null,

    @Schema(description = "Chinese product description (optional; falls back to English)")
    val descriptionZh: String? = null,

    @Schema(description = "Spanish product description (optional; falls back to English)")
    val descriptionEs: String? = null,

    @Schema(description = "Georgian product description (optional; falls back to English)")
    val descriptionKa: String? = null,

    @Schema(description = "List of category IDs the product belongs to", example = "[1, 2]")
    val categoryIds: List<Long>,

    @Schema(description = "List of size IDs the product is available in", example = "[1, 3, 5]")
    val sizeIds: List<Long> = emptyList(),

    @Schema(description = "Product price in EUR", example = "1000")
    val price: BigDecimal,

    @Schema(description = "Whether product is marked as new", example = "true")
    @get:JsonProperty("isNew")
    @param:JsonProperty("isNew")
    val isNew: Boolean = false,

    @Schema(description = "Product status (ACTIVE, INACTIVE, SOLD, BOOKED)", example = "ACTIVE")
    val status: String
)
