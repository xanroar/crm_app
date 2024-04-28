package com.example.crm.domain.user.model

import com.example.crm.domain.permission.Permission
import com.example.crm.domain.role.Role

data class UserDTO(
    var email: String,
    var password: String,
    var role: Set<Role> = setOf(),
    var permissions: Set<Permission> = setOf(),
){
    constructor(email: String, password: String, permissions: Set<Permission>) : this(email, password, setOf(), permissions)
}