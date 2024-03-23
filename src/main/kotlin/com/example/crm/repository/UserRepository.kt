package com.example.crm.repository

import com.example.crm.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface UserRepository : JpaRepository<User?, UUID?> {
    fun findByEmail(email: String?): Optional<User?>?
}