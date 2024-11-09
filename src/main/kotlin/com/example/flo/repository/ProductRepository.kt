package com.example.flo.repository

import com.example.flo.model.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, Long> {
  fun findDistinctByCategories_IdIn(categoryIds: List<Long>): List<Product>
}