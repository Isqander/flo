package com.example.flo.controller

import com.example.flo.model.Category
import com.example.flo.model.Product
import com.example.flo.model.Status
import com.example.flo.repository.CategoryRepository
import com.example.flo.service.ProductService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.math.BigDecimal

@RestController
@RequestMapping("/api/products")
class ProductController(
  private val productService: ProductService,
  private val categoryRepository: CategoryRepository
) {

  @PostMapping
  fun createProduct(
    @RequestParam("name") name: String,
    @RequestParam("description") description: String,
    @RequestParam("categoryIds") categoryIds: List<Long>,
    @RequestParam("price") price: BigDecimal,
    @RequestParam("status") status: String,
    @RequestParam("photos") photos: List<MultipartFile>?
  ): ResponseEntity<Product> {
    val categories: List<Category> = categoryIds.map { categoryRepository.findById(it)
      .orElseThrow { Exception("Category not found") } }


    val product = Product(
      name = name,
      description = description,
      categories = categories,
      price = price,
      status = Status.valueOf(status.uppercase()),
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
    @RequestBody updatedProduct: Product,
    @RequestParam("photos", required = false) photos: List<MultipartFile>?
  ): ResponseEntity<Product> {
    val product = productService.updateProduct(id, updatedProduct, photos)
    return ResponseEntity.ok(product)
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