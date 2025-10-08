package com.example.flo.DTO

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Response containing a JWT access token")
data class TokenResponse(
    @Schema(description = "JWT access token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    val token: String
)
