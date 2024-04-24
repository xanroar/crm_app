package com.example.crm.domain.permission

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class PermissionService(private val permissionRepository: PermissionRepository) {
    fun getAllPermissions(pageable: Pageable): Page<Permission?> {
        return permissionRepository.findAll(pageable)
    }

    fun savePermission(permission: Permission): Permission {
        return permissionRepository.save(permission)
    }

    fun deletePermission(id: UUID){
        if (!permissionRepository.existsById(id)) {
            throw NoSuchElementException("Order with id $id not found")
        }
        permissionRepository.deleteById(id)
    }
}
