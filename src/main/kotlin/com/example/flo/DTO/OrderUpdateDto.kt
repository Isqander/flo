package com.example.flo.DTO

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

@Schema(description = "Data transfer object for updating order status")
data class OrderUpdateDto(
    @field:NotBlank(message = "Status is required")
    @Schema(description = "Updated order status (NEW, COMPLETED, CANCELED)", example = "COMPLETED")
    val status: String
)
