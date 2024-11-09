package com.example.flo.service

import com.example.flo.model.Category
import com.example.flo.repository.CategoryRepository
import org.springframework.stereotype.Service

@Service
class CategoryService(private val categoryRepository: CategoryRepository) {
  fun saveCategory(category: Category): Category = categoryRepository.save(category)
  fun getAllCategories(): List<Category> = categoryRepository.findAll()
  fun deleteCategoryById(id: Long) = categoryRepository.deleteById(id)
}