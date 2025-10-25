package com.example.flo.service

import com.example.flo.exception.ResourceNotFoundException
import com.example.flo.model.Category
import com.example.flo.repository.CategoryRepository
import org.springframework.stereotype.Service

@Service
class CategoryService(private val categoryRepository: CategoryRepository) {
  fun saveCategory(category: Category): Category = categoryRepository.save(category)
  fun getAllCategories(): List<Category> = categoryRepository.findByDeletedFalse()
  fun deleteCategoryById(id: Long) {
    val category = categoryRepository.findById(id)
      .orElseThrow { ResourceNotFoundException("Category not found with id: $id") }

    // Soft delete: mark as deleted instead of physically removing
    categoryRepository.save(category.copy(deleted = true))
  }
}