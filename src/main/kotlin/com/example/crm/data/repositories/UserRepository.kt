package com.example.crm.data.repositories

import com.example.crm.data.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface UserRepository : JpaRepository<User?, UUID?> {
    fun findByEmail(email: String?): Optional<User?>?
}