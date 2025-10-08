package com.example.flo.controller

import com.example.flo.DTO.TokenResponse
import com.example.flo.security.JwtService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication API", description = "Endpoints for obtaining administrative access tokens")
class AuthController(
    private val jwtService: JwtService
) {

    @Operation(
        summary = "Generate JWT token",
        description = "Issues a JWT token for authenticated administrator requests"
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "JWT token generated successfully",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = TokenResponse::class))]
            ),
            ApiResponse(responseCode = "401", description = "Invalid credentials", content = [Content()])
        ]
    )
    @SecurityRequirement(name = "basicAuth")
    @PostMapping("/token")
    fun generateToken(authentication: Authentication): ResponseEntity<TokenResponse> {
        val token = jwtService.generateToken(authentication.name)
        return ResponseEntity.status(HttpStatus.OK).body(TokenResponse(token))
    }
}
