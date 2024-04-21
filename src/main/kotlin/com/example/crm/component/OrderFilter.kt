package com.example.crm.component

import org.springframework.stereotype.Component

@Component
class OrderFilter {
    fun filterOrderNumber(orderNumber: String?): String? {
        return orderNumber?.replace(Regex("\\D"), "")
    }
}