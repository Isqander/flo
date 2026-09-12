package com.example.flo.model

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Schema(description = "Product entity representing items for sale")
data class Product(
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long = 0,
  val name: String,
  @Column(name = "name_en")
  val nameEn: String? = null,
  @Column(name = "name_ru")
  val nameRu: String? = null,
  @Column(name = "name_zh")
  val nameZh: String? = null,
  @Column(name = "name_es")
  val nameEs: String? = null,
  @Column(name = "name_ka")
  val nameKa: String? = null,
  val description: String,
  @Column(name = "description_en")
  val descriptionEn: String? = null,
  @Column(name = "description_ru")
  val descriptionRu: String? = null,
  @Column(name = "description_zh")
  val descriptionZh: String? = null,
  @Column(name = "description_es")
  val descriptionEs: String? = null,
  @Column(name = "description_ka")
  val descriptionKa: String? = null,
  @Column(name = "is_new", nullable = false, columnDefinition = "boolean default false not null")
  @get:JsonProperty("isNew")
  @param:JsonProperty("isNew")
  val isNew: Boolean = false,
  @ManyToMany(cascade = [(CascadeType.MERGE)], fetch = FetchType.LAZY)
  @JoinTable(
    name = "product_category",
    joinColumns = [JoinColumn(name = "product_id")],
    inverseJoinColumns = [JoinColumn(name = "category_id")]
  )
  @JsonManagedReference
  val categories: List<Category>,
  @ManyToMany(cascade = [(CascadeType.MERGE)], fetch = FetchType.LAZY)
  @JoinTable(
    name = "product_size",
    joinColumns = [JoinColumn(name = "product_id")],
    inverseJoinColumns = [JoinColumn(name = "size_id")]
  )
  @JsonManagedReference
  val sizes: List<Size> = listOf(),
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
  @Column(name = "name_en")
  val nameEn: String? = null,
  @Column(name = "name_ru")
  val nameRu: String? = null,
  @Column(name = "name_zh")
  val nameZh: String? = null,
  @Column(name = "name_es")
  val nameEs: String? = null,
  @Column(name = "name_ka")
  val nameKa: String? = null,
  @Column(columnDefinition = "integer default 0 not null")
  val sortOrder: Int = 0,
  val deleted: Boolean = false,
  @ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY)
  @JsonBackReference
  val products: List<Product> = listOf()
)

@Entity
@Schema(description = "Size entity representing product sizes")
data class Size(
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long = 0,
  val name: String,
  @Column(name = "name_en")
  val nameEn: String? = null,
  @Column(name = "name_ru")
  val nameRu: String? = null,
  @Column(name = "name_zh")
  val nameZh: String? = null,
  @Column(name = "name_es")
  val nameEs: String? = null,
  @Column(name = "name_ka")
  val nameKa: String? = null,
  @Column(columnDefinition = "integer default 0 not null")
  val sortOrder: Int = 0,
  val deleted: Boolean = false,
  @ManyToMany(mappedBy = "sizes", fetch = FetchType.LAZY)
  @JsonBackReference
  val products: List<Product> = listOf()
)
