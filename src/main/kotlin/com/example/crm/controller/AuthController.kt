package com.example.crm.controller

import com.example.crm.dto.*
import com.example.crm.service.AuthService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class AuthController(
    private val authenticationService: AuthService
) {
    @PostMapping("/v1/auth")
    fun authenticate(@RequestBody authRequest: AuthRequest): AuthResponse =
        authenticationService.authentication(authRequest)

    @PostMapping("/v1/register")
    fun registerUser(@RequestBody userDTO: UserDTO): UserDTO =
        authenticationService.registerUser(userDTO)

    @PostMapping("/v1/logout")
    fun logoutUser(@RequestHeader(value="Authorization")user: String) =
        authenticationService.deleteRefreshTokenByUserId(user)

    @PostMapping("/v1/refresh")
    fun refreshAccessToken(@RequestHeader(value="Authorization")refreshToken: String): RefreshResponse? =
        authenticationService.refreshAccessToken(refreshToken)

}

