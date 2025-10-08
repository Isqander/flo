package com.example.flo.model

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Schema(description = "Product entity representing items for sale")
data class Product(
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long = 0,
  val name: String,
  val description: String,
  @ManyToMany(cascade = [(CascadeType.MERGE)], fetch = FetchType.LAZY)
  @JoinTable(
    name = "product_category",
    joinColumns = [JoinColumn(name = "product_id")],
    inverseJoinColumns = [JoinColumn(name = "category_id")]
  )
  @JsonManagedReference
  val categories: List<Category>,
  val price: BigDecimal,
  @Enumerated(EnumType.STRING)
  var status: Status,
  @ElementCollection
  val photos: List<String>?
)

enum class Status {
  SOLD, ACTIVE, INACTIVE, BOOKED
}

@Entity
@Schema(description = "Category entity representing product groupings")
data class Category(
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long = 0,
  val name: String,
  @ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY)
  @JsonBackReference
  val products: List<Product> = listOf()
)