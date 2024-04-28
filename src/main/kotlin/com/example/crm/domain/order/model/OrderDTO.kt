package com.example.crm.domain.order.model

data class OrderDTO(
    var orderNumber: String? = null,
    var totalPrice: Double? = null,
    var status: OrderStatus
)
