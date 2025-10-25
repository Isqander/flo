package com.example.flo.DTO

import com.example.flo.model.Category
import com.example.flo.model.Product
import com.example.flo.model.Size
import com.example.flo.model.Status
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

    @Schema(description = "Product price", example = "1000")
    val price: BigDecimal,

    @Schema(description = "Product status")
    val status: Status,

    @Schema(description = "Product thumbnail photos")
    val thumbnails: List<String>?
) {
    companion object {
        fun fromProduct(product: Product): ProductListDto {
            return ProductListDto(
                id = product.id,
                name = product.name,
                description = product.description,
                categories = product.categories,
                sizes = product.sizes,
                price = product.price,
                status = product.status,
                thumbnails = product.photos?.map { "thumb_$it" }
            )
        }
    }
}
