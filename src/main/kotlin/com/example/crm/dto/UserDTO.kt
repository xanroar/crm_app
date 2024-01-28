package com.example.crm.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import com.example.crm.enums.Role

class UserDTO {
    var id: Long = 0
    var role: Role? = null
    var username: String? = null
    var fullName: String? = null
    var password: String? = null
    var enabled: Boolean? = false
}


