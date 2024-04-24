package com.example.crm.domain.user.model

import com.example.crm.domain.permission.Permission
import com.example.crm.domain.role.Role
import java.util.*

data class UserDTO(
    var id: UUID? = null,
    var email: String? = null,
    var password: String? = null,
    var role: Set<Role>? = null,
    var permissions: Set<Permission>? = null
)