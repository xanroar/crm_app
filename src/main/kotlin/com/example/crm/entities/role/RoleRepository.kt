package com.example.crm.entities.role

import com.example.crm.entities.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RoleRepository : JpaRepository<Role, UUID?> {
    fun findByName(name: String?): Optional<Role?>?

}