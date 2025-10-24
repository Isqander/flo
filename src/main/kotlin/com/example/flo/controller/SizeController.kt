package com.example.flo.controller

import com.example.flo.model.Size
import com.example.flo.service.SizeService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/sizes")
@Tag(name = "Size API", description = "API for managing product sizes")
class SizeController(private val sizeService: SizeService) {

    @Operation(summary = "Create a new size", description = "Create a new product size")
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "Size created successfully",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = Size::class))]),
        ApiResponse(responseCode = "400", description = "Invalid input data", content = [Content()])
    ])
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    fun createSize(
        @Parameter(description = "Size data", required = true)
        @RequestBody size: Size
    ): ResponseEntity<Size> {
        val savedSize = sizeService.saveSize(size)
        return ResponseEntity(savedSize, HttpStatus.CREATED)
    }

    @Operation(summary = "Get all sizes", description = "Returns all product sizes")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "List of sizes",
            content = [Content(mediaType = "application/json", array = ArraySchema(schema = Schema(implementation = Size::class)))])
    ])
    @GetMapping
    fun getAllSizes(): ResponseEntity<List<Size>> {
        val sizes = sizeService.getAllSizes()
        return ResponseEntity.ok(sizes)
    }

    @Operation(summary = "Delete a size", description = "Delete a size by its ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "204", description = "Size deleted successfully", content = [Content()]),
        ApiResponse(responseCode = "404", description = "Size not found", content = [Content()])
    ])
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{id}")
    fun deleteSize(
        @Parameter(description = "Size ID", required = true)
        @PathVariable id: Long
    ): ResponseEntity<Void> {
        sizeService.deleteSizeById(id)
        return ResponseEntity.noContent().build()
    }
}
