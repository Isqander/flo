package com.example.flo.service

import com.example.flo.DTO.OrderDto
import com.example.flo.exception.BadRequestException
import com.example.flo.exception.ResourceNotFoundException
import com.example.flo.model.Order
import com.example.flo.model.OrderStatus
import com.example.flo.model.Product
import com.example.flo.model.Status
import com.example.flo.repository.OrderRepository
import com.example.flo.repository.ProductRepository
import jakarta.servlet.http.HttpSession
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
        // Validate that product IDs are provided
        if (orderDto.productIds.isEmpty()) {
            throw BadRequestException("Order must contain at least one product")
        }

        // Get products by IDs
        val products = productRepository.findAllById(orderDto.productIds)
        if (products.size != orderDto.productIds.size) {
            val foundIds = products.map { it.id }
            val missingIds = orderDto.productIds.filter { it !in foundIds }
            throw ResourceNotFoundException("Products not found with ids: $missingIds")
        }

        // Check if any products are already booked or sold
        val unavailableProducts = products.filter { it.status != Status.ACTIVE }
        if (unavailableProducts.isNotEmpty()) {
            val unavailableIds = unavailableProducts.map { "${it.id} (${it.status})" }
            throw BadRequestException("Some products are not available: $unavailableIds")
        }

        // Mark products as booked for the order
        products.forEach { it.status = Status.BOOKED }

        // Create and save the order
        val order = Order(
            customerName = orderDto.customerName,
            products = products,
            email = orderDto.email,
            phone = orderDto.phone,
            telegramUsername = orderDto.telegramUsername,
            customerComment = orderDto.customerComment
        )

        val savedOrder = orderRepository.save(order)

        // Send notification via Telegram (non-blocking, errors logged but don't fail the order)
        try {
            sendOrderNotification(savedOrder, products)
        } catch (e: Exception) {
            // Log the error but don't fail the order creation
            println("Failed to send Telegram notification: ${e.message}")
        }

        return savedOrder
    }

    fun getOrderById(id: Long): Order {
        return orderRepository.findById(id).orElseThrow {
            ResourceNotFoundException("Order not found with id: $id")
        }
    }

    fun getAllOrders(): List<Order> {
        return orderRepository.findAll()
    }

    fun addOrderToSession(session: HttpSession, orderId: Long) {
        val existingIds = getSessionOrderIds(session).toMutableList()
        existingIds.remove(orderId)
        existingIds.add(0, orderId)
        session.setAttribute(SESSION_ORDER_IDS_KEY, existingIds.take(MAX_SESSION_ORDER_IDS))
    }

    fun getOrdersFromSession(session: HttpSession): List<Order> {
        val ids = getSessionOrderIds(session)
        if (ids.isEmpty()) return emptyList()
        return orderRepository.findAllById(ids).sortedByDescending { it.createdAt }
    }

    fun getCustomerOrders(email: String?, phone: String?, telegramUsername: String?): List<Order> {
        val normalizedEmail = email?.trim()?.takeIf { it.isNotEmpty() }
        val normalizedPhone = phone?.trim()?.takeIf { it.isNotEmpty() }
        val normalizedTelegram = telegramUsername?.trim()?.takeIf { it.isNotEmpty() }

        if (normalizedEmail == null && normalizedPhone == null && normalizedTelegram == null) {
            throw BadRequestException("At least one filter is required: email, phone, or telegramUsername")
        }

        return orderRepository.findCustomerOrders(
            email = normalizedEmail,
            phone = normalizedPhone,
            telegramUsername = normalizedTelegram
        )
    }

    @Transactional
    fun updateOrderStatus(id: Long, newStatus: OrderStatus): Order {
        val order = orderRepository.findById(id).orElseThrow {
            ResourceNotFoundException("Order not found with id: $id")
        }

        order.status = newStatus

        when (newStatus) {
            OrderStatus.NEW -> order.products.forEach { it.status = Status.BOOKED }
            OrderStatus.COMPLETED -> order.products.forEach { it.status = Status.SOLD }
            OrderStatus.CANCELED -> order.products.forEach { it.status = Status.ACTIVE }
        }

        return order
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
            ${order.customerComment?.let { "<b>Комментарий:</b> $it\n" } ?: ""}

            <b>Товары:</b>
            $productsList

            <b>Итого:</b> $totalPrice₽
            <b>Дата:</b> ${order.createdAt}
        """.trimIndent()

        telegramService.sendTextMessage(message)
    }

    private fun getSessionOrderIds(session: HttpSession): List<Long> {
        val raw = session.getAttribute(SESSION_ORDER_IDS_KEY) as? List<*> ?: return emptyList()
        return raw.mapNotNull { value ->
            when (value) {
                is Number -> value.toLong()
                is String -> value.toLongOrNull()
                else -> null
            }
        }.distinct()
    }

    companion object {
        private const val SESSION_ORDER_IDS_KEY = "customerOrderIds"
        private const val MAX_SESSION_ORDER_IDS = 100
    }
}
