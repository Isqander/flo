package com.example.flo.service

import com.example.flo.exception.ResourceNotFoundException
import com.example.flo.model.Category
import com.example.flo.repository.CategoryRepository
import org.springframework.stereotype.Service

@Service
class CategoryService(private val categoryRepository: CategoryRepository) {
  fun saveCategory(category: Category): Category = categoryRepository.save(category)
  fun getAllCategories(): List<Category> = categoryRepository.findByDeletedFalseOrderBySortOrderAsc()
  fun deleteCategoryById(id: Long) {
    val category = categoryRepository.findById(id)
      .orElseThrow { ResourceNotFoundException("Category not found with id: $id") }

    // Soft delete: mark as deleted instead of physically removing
    categoryRepository.save(category.copy(deleted = true))
  }

  fun reorderCategories(ids: List<Long>): List<Category> {
    if (ids.isEmpty()) return getAllCategories()
    val categories = categoryRepository.findAllById(ids)
    if (categories.size != ids.size) {
      val foundIds = categories.map { it.id }.toSet()
      val missingIds = ids.filter { it !in foundIds }
      throw ResourceNotFoundException("Categories not found: $missingIds")
    }
    val byId = categories.associateBy { it.id }
    val reordered = ids.mapIndexed { index, id ->
      byId.getValue(id).copy(sortOrder = index)
    }
    return categoryRepository.saveAll(reordered).toList()
  }
}