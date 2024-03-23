package com.example.crm.dto

import com.example.crm.model.Permission
import com.example.crm.model.Role
import java.util.*

data class UserDTO(
    var id: UUID? = null,
    var email: String? = null,
    var password: String? = null,
    var role: Role? = null,
    var permissions: Set<Permission>? = null
)