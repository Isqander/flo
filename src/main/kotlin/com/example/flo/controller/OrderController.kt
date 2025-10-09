package com.example.flo.controller

import com.example.flo.DTO.OrderDto
import com.example.flo.DTO.OrderUpdateDto
import com.example.flo.model.Order
import com.example.flo.model.OrderStatus
import com.example.flo.service.OrderService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/orders")
@Tag(name = "Order API", description = "API for managing customer orders")
class OrderController(private val orderService: OrderService) {

    @Operation(summary = "Create a new order", description = "Create a new customer order")
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "Order created successfully",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = Order::class))]),
        ApiResponse(responseCode = "400", description = "Invalid input data", content = [Content()]),
        ApiResponse(responseCode = "404", description = "Product not found", content = [Content()])
    ])
    @PostMapping
    fun createOrder(
        @Parameter(description = "Order data", required = true)
        @RequestBody orderDto: OrderDto
    ): ResponseEntity<Order> {
        val order = orderService.createOrder(orderDto)
        return ResponseEntity(order, HttpStatus.CREATED)
    }

    @Operation(summary = "Get order by ID", description = "Returns an order by its ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Order found",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = Order::class))]),
        ApiResponse(responseCode = "404", description = "Order not found", content = [Content()])
    ])
    @GetMapping("/{id}")
    fun getOrderById(
        @Parameter(description = "Order ID", required = true)
        @PathVariable id: Long
    ): ResponseEntity<Order> {
        val order = orderService.getOrderById(id)
        return ResponseEntity.ok(order)
    }

    @Operation(summary = "Get all orders", description = "Returns all orders in the system")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "List of orders",
            content = [Content(mediaType = "application/json", array = ArraySchema(schema = Schema(implementation = Order::class)))])
    ])
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping
    fun getAllOrders(): ResponseEntity<List<Order>> {
        val orders = orderService.getAllOrders()
        return ResponseEntity.ok(orders)
    }

    @Operation(summary = "Update order status", description = "Update the status of an existing order")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Order updated successfully",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = Order::class))]),
        ApiResponse(responseCode = "400", description = "Invalid input data", content = [Content()]),
        ApiResponse(responseCode = "404", description = "Order not found", content = [Content()])
    ])
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/{id}")
    fun updateOrderStatus(
        @Parameter(description = "Order ID", required = true)
        @PathVariable id: Long,
        @Parameter(description = "Updated order status", required = true)
        @RequestBody updateDto: OrderUpdateDto
    ): ResponseEntity<Order> {
        val status = OrderStatus.valueOf(updateDto.status.uppercase())
        val updatedOrder = orderService.updateOrderStatus(id, status)
        return ResponseEntity.ok(updatedOrder)
    }
}
