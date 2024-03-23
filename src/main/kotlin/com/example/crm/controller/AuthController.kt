package com.example.crm.controller

import com.example.crm.dto.*
import com.example.crm.model.User
import com.example.crm.service.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api")
class AuthController(
    private val authenticationService: AuthService
) {
    @PostMapping("/auth")
    fun authenticate(@RequestBody authRequest: AuthRequest): AuthResponse =
        authenticationService.authentication(authRequest)

    @PostMapping("/register")
    fun registerUser(@RequestBody userDTO: UserDTO): UserDTO =
        authenticationService.registerUser(userDTO)

    @PostMapping("/logout")
    fun logoutUser(@RequestBody userid: Map<String, UUID>) =
        userid["id"]?.let { authenticationService.deleteRefreshTokenByUserId(it) }

    @PostMapping("/refresh")
    fun refreshAccessToken(@RequestBody request: RefreshRequest): RefreshResponse? =
        authenticationService.refreshAccessToken(request)

}

