package com.example.flo.DTO

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Request body for reordering categories or sizes by ID list")
data class ReorderIdsDto(
    @Schema(description = "List of entity IDs in the desired order", example = "[2, 3, 5, 7, 13, 14, 16, 15, 17, 18, 19]", required = true)
    val ids: List<Long>
)
