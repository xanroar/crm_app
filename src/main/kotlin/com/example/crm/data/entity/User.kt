package com.example.crm.data.entity

import com.example.crm.enums.Role
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "user_table")
class User {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
    val role: Role? = null
    val username: String? = null
    val fullName: String? = null
    val password: String? = null
    val enabled: Boolean = true

}