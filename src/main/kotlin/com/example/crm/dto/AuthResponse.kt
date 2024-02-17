package com.example.crm.dto

data class AuthResponse(
    val accessToken: String,
    val refreshToken: String,
)