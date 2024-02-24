package com.example.crm.entities.user

import com.example.crm.entities.permission.Permission
import com.example.crm.entities.role.Role
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "users")
data class User(
    @Id
    var id: UUID = UUID.randomUUID(),

    @Column(name = "email")
    var email: String? = null,

    @Column(name = "password")
    var password: String? = null,


    @ManyToOne
    @JoinColumn(name = "role_id")
    var role: Role? = null,

    @ManyToMany
    @JoinTable(
        name = "user_to_permission",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "permission_id")]
    )
    var permissions: Set<Permission> = HashSet()
)



