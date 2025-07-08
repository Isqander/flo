package com.example.flo.controller

import com.example.flo.model.Category
import com.example.flo.service.CategoryService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/categories")
@Tag(name = "Category API", description = "API for managing product categories")
class CategoryController(private val categoryService: CategoryService) {

    @Operation(summary = "Create a new category", description = "Create a new product category")
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "Category created successfully",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = Category::class))]),
        ApiResponse(responseCode = "400", description = "Invalid input data", content = [Content()])
    ])
    @PostMapping
    fun createCategory(
        @Parameter(description = "Category data", required = true)
        @RequestBody category: Category
    ): ResponseEntity<Category> {
        val savedCategory = categoryService.saveCategory(category)
        return ResponseEntity(savedCategory, HttpStatus.CREATED)
    }

    @Operation(summary = "Get all categories", description = "Returns all product categories")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "List of categories",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = Category::class))])
    ])
    @GetMapping
    fun getAllCategories(): ResponseEntity<List<Category>> {
        val categories = categoryService.getAllCategories()
        return ResponseEntity.ok(categories)
    }

    @Operation(summary = "Delete a category", description = "Delete a category by its ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "204", description = "Category deleted successfully", content = [Content()]),
        ApiResponse(responseCode = "404", description = "Category not found", content = [Content()])
    ])
    @DeleteMapping("/{id}")
    fun deleteCategory(
        @Parameter(description = "Category ID", required = true)
        @PathVariable id: Long
    ): ResponseEntity<Void> {
        categoryService.deleteCategoryById(id)
        return ResponseEntity.noContent().build()
    }
}
