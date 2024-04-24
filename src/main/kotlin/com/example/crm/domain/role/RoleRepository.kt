package com.example.crm.domain.role

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RoleRepository : JpaRepository<Role, UUID?> {
    fun findByNameIn(names: Set<String>?): Set<Role?>
}