package com.example.flo.model

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import java.math.BigDecimal

@Entity
data class Product(
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long = 0,
  val name: String,
  val description: String,
  @ManyToMany(cascade = [(CascadeType.MERGE)])
  @JoinTable(
    name = "product_category",
    joinColumns = [JoinColumn(name = "product_id")],
    inverseJoinColumns = [JoinColumn(name = "category_id")]
  )
  @JsonManagedReference
  val categories: List<Category>,
  val price: BigDecimal,
  @Enumerated(EnumType.STRING)
  val status: Status,
  @ElementCollection
  val photos: List<String>?
)

enum class Status {
  SOLD, ACTIVE, INACTIVE
}

@Entity
data class Category(
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long = 0,
  val name: String,
  @ManyToMany(mappedBy = "categories")
  @JsonBackReference
  val products: List<Product> = listOf()
)