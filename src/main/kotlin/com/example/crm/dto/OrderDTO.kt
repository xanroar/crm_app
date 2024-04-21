package com.example.crm.dto

import com.example.crm.model.OrderStatus
import java.util.*

data class OrderDTO(
    var orderNumber: String? = null,
    var totalPrice: Double? = null,
    var status: OrderStatus? = null
)
