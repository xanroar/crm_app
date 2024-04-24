package com.example.crm.domain.order

import com.example.crm.domain.order.model.Order
import com.example.crm.domain.order.model.OrderStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface OrderRepository : JpaRepository<Order?, UUID?> {
     fun findAllByUserId(userId: UUID): List<Order>
     fun findAllByUserIdAndStatus(userId: UUID, status: OrderStatus): List<Order>
}