package com.example.flo.repository

import com.example.flo.model.Order
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface OrderRepository : JpaRepository<Order, Long> {

    @Query(
        """
        SELECT o FROM Order o
        WHERE (:email IS NOT NULL AND o.email = :email)
           OR (:phone IS NOT NULL AND o.phone = :phone)
           OR (:telegramUsername IS NOT NULL AND o.telegramUsername = :telegramUsername)
        ORDER BY o.createdAt DESC
        """
    )
    fun findCustomerOrders(
        @Param("email") email: String?,
        @Param("phone") phone: String?,
        @Param("telegramUsername") telegramUsername: String?
    ): List<Order>
}
