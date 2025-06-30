package com.example.flo.service

import com.example.flo.DTO.OrderDto
import com.example.flo.model.Order
import com.example.flo.model.Product
import com.example.flo.repository.OrderRepository
import com.example.flo.repository.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val productRepository: ProductRepository,
    private val telegramService: TelegramService
) {

    @Transactional
    fun createOrder(orderDto: OrderDto): Order {
        // Validate that at least one contact method is provided
        require(orderDto.email != null || orderDto.phone != null || orderDto.telegramUsername != null) {
            "At least one contact method (email, phone, or telegram username) must be provided"
        }

        // Get products by IDs
        val products = productRepository.findAllById(orderDto.productIds)
        if (products.size != orderDto.productIds.size) {
            val foundIds = products.map { it.id }
            val missingIds = orderDto.productIds.filter { it !in foundIds }
            throw IllegalArgumentException("Products not found with ids: $missingIds")
        }

        // Create and save the order
        val order = Order(
            customerName = orderDto.customerName,
            products = products,
            email = orderDto.email,
            phone = orderDto.phone,
            telegramUsername = orderDto.telegramUsername
        )

        val savedOrder = orderRepository.save(order)

        // Send notification via Telegram
//        sendOrderNotification(savedOrder, products)

        return savedOrder
    }

    fun getOrderById(id: Long): Order {
        return orderRepository.findById(id).orElseThrow {
            IllegalArgumentException("Order not found with id: $id")
        }
    }

    fun getAllOrders(): List<Order> {
        return orderRepository.findAll()
    }

    private fun sendOrderNotification(order: Order, products: List<Product>) {
        val totalPrice = products.sumOf { it.price }
        val productsList = products.joinToString("\n") { "- ${it.name} (${it.price}₽)" }

        val message = """
            <b>Новый заказ #${order.id}</b>

            <b>Клиент:</b> ${order.customerName}
            ${order.email?.let { "<b>Email:</b> $it\n" } ?: ""}
            ${order.phone?.let { "<b>Телефон:</b> $it\n" } ?: ""}
            ${order.telegramUsername?.let { "<b>Telegram:</b> $it\n" } ?: ""}

            <b>Товары:</b>
            $productsList

            <b>Итого:</b> $totalPrice₽
            <b>Дата:</b> ${order.createdAt}
        """.trimIndent()

        telegramService.sendTextMessage(message)
    }
}
