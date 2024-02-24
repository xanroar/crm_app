package com.example.crm.entities.user

import com.example.crm.entities.permission.Permission
import com.example.crm.entities.role.Role
import java.util.*

data class UserDTO(
    var id: UUID? = null,
    var email: String? = null,
    var password: String? = null,
    var role: Role? = null,
    var permissions: Set<Permission> = HashSet()
)