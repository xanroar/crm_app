package com.example.crm.repository

import com.example.crm.model.Permission
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface PermissionRepository : JpaRepository<Permission, UUID?> {
    fun findByNameIn(names: Set<String>?): Set<Permission?>
}
