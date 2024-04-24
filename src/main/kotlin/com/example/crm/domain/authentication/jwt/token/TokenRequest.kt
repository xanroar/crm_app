package com.example.crm.domain.authentication.jwt.token

data class TokenRequest(
    val email: String,
    val password: String,
)