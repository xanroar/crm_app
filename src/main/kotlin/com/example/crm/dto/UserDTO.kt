package com.example.crm.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import com.example.crm.enums.Role

class UserDTO {
    var id: Long = 0

    @NotNull
    var role: Role? = null

    @NotBlank
    var username: String? = null

    @NotBlank
    var fullName: String? = null

    var password: String? = null

    var enabled: Boolean? = false
}


