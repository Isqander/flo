package com.example.flo.model

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
data class Product(
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long = 0,
  val name: String,
  val description: String,
  val category: String,
  val price: BigDecimal,
  @Enumerated(EnumType.STRING)
  val status: Status,
  @ElementCollection
  val photos: List<String>?
)

enum class Status {
  SOLD, ACTIVE, INACTIVE
}