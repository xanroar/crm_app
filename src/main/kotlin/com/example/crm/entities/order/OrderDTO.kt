package com.example.crm.entities.order

import java.util.*

data class OrderDTO(
    var id: UUID? = null,
    var orderNumber: String? = null,
    var totalPrice: Double? = null,
    var userId: UUID? = null,
)
