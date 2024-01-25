package com.example.crm.data.entity

import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

@Entity
class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
    val firstName: String? = null
    val lastName: String? = null
    val emailAddress: String? = null

    val address: String? = null
    val city: String? = null
    val country: String? = null
    val phoneNumber: String? = null
}