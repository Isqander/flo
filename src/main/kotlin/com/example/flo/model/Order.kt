package com.example.flo.model

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "purchase_orders")
@Schema(description = "Order entity representing customer purchases")
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

    @Column(length = 1000)
    val customerComment: String? = null,

    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Enumerated(EnumType.STRING)
    var status: OrderStatus = OrderStatus.NEW
)

@Schema(description = "Enum representing different order statuses")
enum class OrderStatus {
    @Schema(description = "New order that has just been placed")
    NEW,
    @Schema(description = "Order has been completed")
    COMPLETED,
    @Schema(description = "Order has been canceled")
    CANCELED
}
