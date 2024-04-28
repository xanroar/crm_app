package com.example.crm.lib

import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicLong

@Service
class IdGenerator {
    private val counter = AtomicLong()

    fun generateOrderId(prefix: String): String {
        val id = counter.incrementAndGet()
        return "$prefix-$id"
    }
}