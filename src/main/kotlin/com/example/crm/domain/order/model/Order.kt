package com.example.crm.domain.order.model

import com.example.crm.domain.user.model.User
import jakarta.persistence.*
import java.util.*
import java.time.LocalDateTime

@Entity
@Table(name = "orders")
data class Order(
    @Column(name = "total_price")
    var totalPrice: Double? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    var status: OrderStatus
) {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", updatable = false, nullable = false)
    var id: UUID? = null

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    var user: User? = null

    @Column(name = "order_number", nullable = false)
    var orderNumber: String? = null

    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime? = null

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime? = null
}
