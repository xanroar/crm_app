package com.example.crm.data.entity

import jakarta.persistence.*
import java.util.*


@Entity
@Table(name = "orders")
data class Order(
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", updatable = false, nullable = false)
    var id: UUID? = null,

    @Column(name = "order_number")
    var orderNumber: String? = null,

    @Column(name = "total_price")
    var totalPrice: Double? = null,

    @Column(name = "user_id")
    var userId: UUID? = null,

)

