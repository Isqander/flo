package com.example.flo.repository

import com.example.flo.model.Category
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository : JpaRepository<Category, Long> {
    fun findByDeletedFalse(): List<Category>
}