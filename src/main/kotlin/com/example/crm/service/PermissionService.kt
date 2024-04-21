package com.example.crm.service

import com.example.crm.model.Permission
import com.example.crm.repository.PermissionRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class PermissionService(private val permissionRepository: PermissionRepository) {
    fun getAllPermission(pageable: Pageable): Page<Permission?> {
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
