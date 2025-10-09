package com.example.flo.DTO

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Data transfer object for updating order status")
data class OrderUpdateDto(
    @Schema(description = "Updated order status (NEW, COMPLETED, CANCELED)", example = "COMPLETED")
    val status: String
)
