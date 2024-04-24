package com.example.crm.domain.authentication.jwt.token

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String,
)