package com.example.flo.repository

import com.example.flo.model.Product
import com.example.flo.model.Status
import com.example.flo.model.Currency
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ProductRepository : JpaRepository<Product, Long> {
  fun findDistinctByCategories_IdInAndStatusIn(categoryIds: List<Long>, statuses: List<Status>): List<Product>

  fun findByStatusIn(statuses: List<Status>): List<Product>

  fun findByIsNewTrueAndStatusNotIn(statuses: List<Status>): List<Product>

  fun findByPriceCurrency(currency: Currency): List<Product>

  @Modifying
  @Query("DELETE FROM order_products WHERE product_id = :productId", nativeQuery = true)
  fun deleteOrderProductLinks(@Param("productId") productId: Long): Int
}
