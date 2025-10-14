package com.example.flo.controller

import com.example.flo.DTO.ProductDto
import com.example.flo.exception.BadRequestException
import com.example.flo.exception.ResourceNotFoundException
import com.example.flo.model.Category
import com.example.flo.model.Product
import com.example.flo.model.Status
import com.example.flo.repository.CategoryRepository
import com.example.flo.service.ProductService
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
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/products")
@Tag(name = "Product API", description = "API for managing products")
class ProductController(
  private val productService: ProductService,
  private val categoryRepository: CategoryRepository
) {

  @Operation(summary = "Create a new product", description = "Create a new product with optional photos")
  @ApiResponses(value = [
    ApiResponse(responseCode = "201", description = "Product created successfully",
      content = [Content(mediaType = "application/json", schema = Schema(implementation = Product::class))]),
    ApiResponse(responseCode = "400", description = "Invalid input data", content = [Content()]),
    ApiResponse(responseCode = "404", description = "Category not found", content = [Content()])
  ])
  @SecurityRequirement(name = "bearerAuth")
  @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
  fun createProduct(
    @Parameter(description = "Product data", required = true)
    @RequestPart("product") productDto: ProductDto,
    @Parameter(description = "Product photos (optional)")
    @RequestPart("photos", required = false) photos: List<MultipartFile>?
  ): ResponseEntity<Product> {
    val categories: List<Category> = productDto.categoryIds.map {
      categoryRepository.findById(it)
        .orElseThrow { ResourceNotFoundException("Category not found with id: $it") }
    }

    val status = try {
      Status.valueOf(productDto.status.uppercase())
    } catch (e: IllegalArgumentException) {
      throw BadRequestException("Invalid product status: '${productDto.status}'. Valid values are: ${Status.values().joinToString()}")
    }

    val product = Product(
      name = productDto.name,
      description = productDto.description,
      categories = categories,
      price = productDto.price,
      status = status,
      photos = null
    )
    val savedProduct = productService.saveProduct(product, photos)
    return ResponseEntity(savedProduct, HttpStatus.CREATED)
  }

  @Operation(summary = "Get product by ID", description = "Returns a product by its ID")
  @ApiResponses(value = [
    ApiResponse(responseCode = "200", description = "Product found",
      content = [Content(mediaType = "application/json", schema = Schema(implementation = Product::class))]),
    ApiResponse(responseCode = "404", description = "Product not found", content = [Content()])
  ])
  @GetMapping("/{id}")
  fun getProductById(
    @Parameter(description = "Product ID", required = true)
    @PathVariable id: Long
  ): ResponseEntity<Product> {
    val product = productService.getProductById(id)
    return ResponseEntity.ok(product)
  }

  @Operation(summary = "Update a product", description = "Update an existing product by ID. New photos are added to existing ones. Specify photo filenames to delete in photosToDelete.")
  @ApiResponses(value = [
    ApiResponse(responseCode = "200", description = "Product updated successfully",
      content = [Content(mediaType = "application/json", schema = Schema(implementation = Product::class))]),
    ApiResponse(responseCode = "400", description = "Invalid input data", content = [Content()]),
    ApiResponse(responseCode = "404", description = "Product or category not found", content = [Content()])
  ])
  @SecurityRequirement(name = "bearerAuth")
  @PutMapping("/{id}", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
  fun updateProduct(
      @Parameter(description = "Product ID", required = true)
      @PathVariable id: Long,
      @Parameter(description = "Updated product data", required = true)
      @RequestPart("product") updatedProductDto: ProductDto,
      @Parameter(description = "New product photos to add (optional)")
      @RequestPart("photos", required = false) photos: List<MultipartFile>?,
      @Parameter(description = "List of photo filenames to delete (optional)")
      @RequestPart("photosToDelete", required = false) photosToDelete: List<String>?
  ): ResponseEntity<Product> {
    val categories = updatedProductDto.categoryIds.map {
      categoryRepository.findById(it)
        .orElseThrow { ResourceNotFoundException("Category not found with id: $it") }
    }

    val status = try {
      Status.valueOf(updatedProductDto.status.uppercase())
    } catch (e: IllegalArgumentException) {
      throw BadRequestException("Invalid product status: '${updatedProductDto.status}'. Valid values are: ${Status.values().joinToString()}")
    }

    val updatedProduct = Product(
        id = id,
        name = updatedProductDto.name,
        description = updatedProductDto.description,
        categories = categories,
        price = updatedProductDto.price,
        status = status,
        photos = null
    )
    val savedProduct = productService.updateProduct(id, updatedProduct, photos, photosToDelete)
    return ResponseEntity.ok(savedProduct)
  }

  @Operation(summary = "Delete a product", description = "Delete a product by its ID")
  @ApiResponses(value = [
    ApiResponse(responseCode = "204", description = "Product deleted successfully", content = [Content()]),
    ApiResponse(responseCode = "404", description = "Product not found", content = [Content()])
  ])
  @SecurityRequirement(name = "bearerAuth")
  @DeleteMapping("/{id}")
  fun deleteProduct(
    @Parameter(description = "Product ID", required = true)
    @PathVariable id: Long
  ): ResponseEntity<Void> {
    productService.deleteProduct(id)
    return ResponseEntity.noContent().build()
  }

  @Operation(summary = "Get all products", description = "Returns all products with optional filtering by category and status")
  @ApiResponses(value = [
    ApiResponse(responseCode = "200", description = "List of products",
      content = [Content(mediaType = "application/json", array = ArraySchema(schema = Schema(implementation = Product::class)))])
  ])
  @GetMapping
  fun getAllProducts(
    @Parameter(description = "Filter by category IDs (optional)")
    @RequestParam("categoryIds", required = false) categoryIds: List<Long>?,
    @Parameter(description = "Filter by product statuses (default: ACTIVE)")
    @RequestParam("statuses", required = false, defaultValue = "ACTIVE") statuses: List<String>
  ): ResponseEntity<List<Product>> {
    val statusEnums = try {
      statuses.map { Status.valueOf(it.uppercase()) }
    } catch (e: IllegalArgumentException) {
      throw BadRequestException("Invalid product status in filter. Valid values are: ${Status.values().joinToString()}")
    }
    val products = productService.getProductsByFilters(categoryIds, statusEnums)
    return ResponseEntity.ok(products)
  }
}