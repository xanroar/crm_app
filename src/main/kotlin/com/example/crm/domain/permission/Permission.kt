package com.example.crm.domain.permission

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "permissions")
data class Permission(
    @Column(nullable = false)
    var name: String = ""
) {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", updatable = false, nullable = false)
    var id: UUID = UUID.randomUUID()
}