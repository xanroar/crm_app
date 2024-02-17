package com.example.crm.data.entity

import jakarta.persistence.*
import java.util.*


@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", updatable = false, nullable = false)
    var id: UUID? = null,

    @Column(name = "email")
    var email: String? = null,

    @Column(name = "password")
    var password: String? = null,

    @Column(name = "role_id")
    var roles: Int
)
