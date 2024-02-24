package com.example.crm.security.authTokens

data class AuthResponse(
    val accessToken: String,
    val refreshToken: String,
)