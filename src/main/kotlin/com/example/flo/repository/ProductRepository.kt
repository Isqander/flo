package com.example.flo.repository

import com.example.flo.model.Product
import com.example.flo.model.Status
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, Long> {
  fun findDistinctByCategories_IdInAndStatusIn(categoryIds: List<Long>, statuses: List<Status>): List<Product>

  fun findByStatusIn(statuses: List<Status>): List<Product>
}