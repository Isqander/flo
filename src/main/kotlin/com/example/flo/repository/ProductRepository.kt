package com.example.flo.repository

import com.example.flo.model.Product
import com.example.flo.model.Status
import com.example.flo.model.Currency
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, Long> {
  fun findDistinctByCategories_IdInAndStatusIn(categoryIds: List<Long>, statuses: List<Status>): List<Product>

  fun findByStatusIn(statuses: List<Status>): List<Product>

  fun findByIsNewTrueAndStatusNotIn(statuses: List<Status>): List<Product>

  fun findByPriceCurrency(currency: Currency): List<Product>
}
