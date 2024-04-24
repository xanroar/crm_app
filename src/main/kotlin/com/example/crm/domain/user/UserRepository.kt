package com.example.crm.domain.user

import com.example.crm.domain.user.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface UserRepository : JpaRepository<User?, UUID?> {
    fun findByEmail(email: String?): Optional<User?>?
}