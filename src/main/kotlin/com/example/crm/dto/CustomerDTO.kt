package com.example.crm.dto

import jakarta.persistence.Id
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

class CustomerDTO {
    var id: Long? = null
    var firstName: String? = null
    var lastName: String? = null
    var emailAddress: String? = null
    var address: String? = null
    var city: String? = null
    var phoneNumber: String? = null
}