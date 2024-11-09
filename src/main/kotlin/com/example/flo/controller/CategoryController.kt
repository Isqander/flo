package com.example.flo.controller

import com.example.flo.model.Category
import com.example.flo.service.CategoryService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/categories")
class CategoryController(private val categoryService: CategoryService) {

  @PostMapping
  fun createCategory(@RequestBody category: Category): ResponseEntity<Category> {
    val savedCategory = categoryService.saveCategory(category)
    return ResponseEntity(savedCategory, HttpStatus.CREATED)
  }

  @GetMapping
  fun getAllCategories(): ResponseEntity<List<Category>> {
    return ResponseEntity.ok(categoryService.getAllCategories())
  }

  @DeleteMapping("/{id}")
  fun deleteCategory(@PathVariable id: Long): ResponseEntity<Void> {
    categoryService.deleteCategoryById(id)
    return ResponseEntity.noContent().build()
  }
}