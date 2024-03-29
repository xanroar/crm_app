package com.example.crm.model

import com.example.crm.model.User
import jakarta.persistence.*
import java.util.*
import java.util.UUID.randomUUID


@Entity
@Table(name = "orders")
data class Order(
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", updatable = false, nullable = false)
    var id: UUID? = randomUUID(),

    @Column(name = "order_number")
    var orderNumber: String? = null,

    @Column(name = "total_price")
    var totalPrice: Double? = null,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    var user: User
)

