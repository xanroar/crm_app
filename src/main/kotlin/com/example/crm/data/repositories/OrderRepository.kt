package com.example.crm.data.repositories

import com.example.crm.data.entity.Order
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface OrderRepository : JpaRepository<Order?, UUID?> {
}