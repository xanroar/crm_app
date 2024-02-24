package com.example.crm.entities.role

import com.example.crm.entities.permission.Permission
import com.example.crm.entities.permission.PermissionRepository
import com.example.crm.entities.user.UserDTO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*


@Service
class RoleService(
    private val roleRepository: RoleRepository,
    private val permissionRepository: PermissionRepository
) {

    fun getAllRole(pageable: Pageable): Page<Role?> {
        return roleRepository.findAll(pageable)
    }
    fun saveRole(role: Role): Role {
        return roleRepository.save(createRole(role))
    }
    private fun createRole(dto: Role): Role {
        val role = Role()
        role.id = dto.id
        role.name = dto.name
        role.permissions = findPermissions(dto.permissions)
        return role
    }
    private fun findPermissions(permissionNames: Set<Permission>): Set<Permission> {
        val permissionNamesAsString = permissionNames.map { it.name }.toSet()
        return permissionRepository.findByNameIn(permissionNamesAsString).filterNotNull().toSet()
    }
    fun deleteRole(id: UUID){
        if (!roleRepository.existsById(id)) {
            throw NoSuchElementException("Order with id $id not found")
        }
        roleRepository.deleteById(id)
    }

    fun getRoleById(id: UUID): Role {
        val permission = roleRepository.findById(id)
            .orElseThrow {
                NoSuchElementException("Order with id $id not found")
            }
        return permission
    }


}
