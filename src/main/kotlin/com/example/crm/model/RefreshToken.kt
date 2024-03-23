package com.example.crm.model

import jakarta.persistence.*
import java.time.Instant
import java.util.*

@Entity
@Table(name = "refresh_tokens")
class RefreshToken(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "user_id", nullable = false)
    val userId: UUID,

    @Column(nullable = false, unique = true)
    val refreshToken: String,

    @Column(nullable = false)
    val expiryDate: Instant

)