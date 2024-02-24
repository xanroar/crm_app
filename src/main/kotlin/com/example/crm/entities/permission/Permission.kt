package com.example.crm.entities.permission

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "permissions")
data class Permission(
    @Id
    var id: UUID = UUID.randomUUID(),

    @Column(nullable = false)
    var name: String = ""
)