package com.example.flo.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "purchase_orders")
data class Order(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val customerName: String,

    @ManyToMany
    @JoinTable(
        name = "order_products",
        joinColumns = [JoinColumn(name = "order_id")],
        inverseJoinColumns = [JoinColumn(name = "product_id")]
    )
    val products: List<Product>,

    val email: String? = null,
    val phone: String? = null,
    val telegramUsername: String? = null,

    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Enumerated(EnumType.STRING)
    val status: OrderStatus = OrderStatus.NEW
)

enum class OrderStatus {
    NEW, PROCESSING, COMPLETED, CANCELLED
}
