package com.example.crm.security.authTokens

data class AuthRequest(
    val email: String,
    val password: String,
)