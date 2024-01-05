package com.example.crm.data.repositories

import com.example.crm.data.model.Customers
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomersRepository : JpaRepository<Customers, Int>
