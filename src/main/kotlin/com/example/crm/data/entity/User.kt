package com.example.crm.data.entity

import com.example.crm.enums.Role
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "appuser")
class User(
    @NotNull
    @Enumerated(EnumType.STRING)
    var role: Role? = null,

    @NotBlank
    @Column(nullable = false)
    var username: String? = null,

    @NotBlank
    @Column(nullable = false)
    var fullName: String? = null,

    @Column(nullable = false, length = 64)
    var password: String? = null,

    var enabled: Boolean = true
) {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @CreationTimestamp
    @Column(updatable = false)
    val dateCreated: LocalDateTime? = null
}