package com.example.crm.domain.permission

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface PermissionRepository : JpaRepository<Permission, UUID?> {
    fun findByNameIn(names: Set<String>?): Set<Permission?>
}
