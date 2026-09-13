package com.example.flo.DTO

import com.fasterxml.jackson.annotation.JsonProperty
import com.example.flo.model.Category
import com.example.flo.model.Product
import com.example.flo.model.Size
import com.example.flo.model.Status
import com.example.flo.service.LocalizationService
import com.example.flo.service.SupportedLanguage
import io.swagger.v3.oas.annotations.media.Schema
import java.math.BigDecimal

@Schema(description = "Product data transfer object for product list with thumbnails")
data class ProductListDto(
    @Schema(description = "Product ID", example = "1")
    val id: Long,

    @Schema(description = "Product name", example = "Tabi Socks")
    val name: String,

    @Schema(description = "Product description", example = "Traditional Japanese split-toe socks")
    val description: String,

    @Schema(description = "Product categories")
    val categories: List<Category>,

    @Schema(description = "Product sizes")
    val sizes: List<Size>,

    @Schema(description = "Product price in EUR", example = "1000")
    val price: BigDecimal,

    @Schema(description = "Whether product is marked as new", example = "true")
    @get:JsonProperty("isNew")
    @param:JsonProperty("isNew")
    val isNew: Boolean,

    @Schema(description = "Product status")
    val status: Status,

    @Schema(description = "Product thumbnail photos")
    val thumbnails: List<String>?
) {
    companion object {
        fun fromProduct(
            product: Product,
            language: SupportedLanguage = SupportedLanguage.EN
        ): ProductListDto {
            val localizedProduct = LocalizationService.localizeProduct(product, language)
            return ProductListDto(
                id = localizedProduct.id,
                name = localizedProduct.name,
                description = localizedProduct.description,
                categories = localizedProduct.categories,
                sizes = localizedProduct.sizes,
                price = localizedProduct.price,
                isNew = localizedProduct.isNew,
                status = localizedProduct.status,
                thumbnails = localizedProduct.photos?.map { "thumb_$it" }
            )
        }
    }
}
