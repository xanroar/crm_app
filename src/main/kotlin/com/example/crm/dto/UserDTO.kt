package com.example.crm.dto

import java.util.*

data class UserDTO(
    var id: UUID? = null,
    var email: String? = null,
    var password: String? = null,
    var roles: Int? = null
)
