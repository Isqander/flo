package com.example.flo.controller

import com.example.flo.DTO.ProductDto
import com.example.flo.model.Category
import com.example.flo.model.Product
import com.example.flo.model.Status
import com.example.flo.repository.CategoryRepository
import com.example.flo.service.ProductService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/products")
class ProductController(
  private val productService: ProductService,
  private val categoryRepository: CategoryRepository
) {

  @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
  fun createProduct(
    @RequestPart("product") productDto: ProductDto,
    @RequestPart("photos", required = false) photos: List<MultipartFile>?
  ): ResponseEntity<Product> {


    val categories: List<Category> = productDto.categoryIds.map { categoryRepository.findById(it)
      .orElseThrow { Exception("Category not found") } }

    val product = Product(
      name = productDto.name,
      description = productDto.description,
      categories = categories,
      price = productDto.price,
      status = Status.valueOf(productDto.status.uppercase()),
      photos = null
    )
    val savedProduct = productService.saveProduct(product, photos)
    return ResponseEntity(savedProduct, HttpStatus.CREATED)
  }

  @GetMapping("/{id}")
  fun getProductById(@PathVariable id: Long): ResponseEntity<Product> {
    val product = productService.getProductById(id)
    return ResponseEntity.ok(product)
  }

  @PutMapping("/{id}")
  fun updateProduct(
      @PathVariable id: Long,
      @RequestBody updatedProductDto: ProductDto,
      @RequestParam("photos", required = false) photos: List<MultipartFile>?
  ): ResponseEntity<Product> {
    val categories = updatedProductDto.categoryIds.map { categoryRepository.findById(it)
        .orElseThrow { Exception("Category not found") } }
    val updatedProduct = Product(
        id = id,
        name = updatedProductDto.name,
        description = updatedProductDto.description,
        categories = categories,
        price = updatedProductDto.price,
        status = Status.valueOf(updatedProductDto.status),
        photos = null
    )
    val savedProduct = productService.updateProduct(id, updatedProduct, photos)
    return ResponseEntity.ok(savedProduct)
  }

  @DeleteMapping("/{id}")
  fun deleteProduct(@PathVariable id: Long): ResponseEntity<Void> {
    productService.deleteProduct(id)
    return ResponseEntity.noContent().build()
  }

  @GetMapping
  fun getAllProducts(
    @RequestParam("categoryIds", required = false) categoryIds: List<Long>?,
    @RequestParam("statuses", required = false, defaultValue = "ACTIVE") statuses: List<String>
  ): ResponseEntity<List<Product>> {
    val statusEnums = statuses.map { Status.valueOf(it.uppercase()) }
    val products = productService.getProductsByFilters(categoryIds, statusEnums)
    return ResponseEntity.ok(products)
  }
}