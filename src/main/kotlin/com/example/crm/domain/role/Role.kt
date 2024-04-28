package com.example.crm.domain.role

import com.example.crm.domain.permission.Permission
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "roles")
data class Role(
    @Column(nullable = false)
    var name: String = "",

    @ManyToMany
    @JoinTable(
        name = "role_to_permission",
        joinColumns = [JoinColumn(name = "role_id")],
        inverseJoinColumns = [JoinColumn(name = "permission_id")]
    )
    var permissions: Set<Permission> = HashSet()
){
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", updatable = false, nullable = false)
    var id: UUID = UUID.randomUUID()
}