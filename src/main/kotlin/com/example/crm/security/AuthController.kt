package com.example.crm.security

import com.example.crm.security.authTokens.AuthRequest
import com.example.crm.security.authTokens.AuthResponse
import com.example.crm.security.authTokens.RefreshRequest
import com.example.crm.security.authTokens.RefreshResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class AuthController(
    private val authenticationService: AuthService
) {

    @PostMapping("/auth")
    fun authenticate(@RequestBody authRequest: AuthRequest): AuthResponse =
        authenticationService.authentication(authRequest)

    @PostMapping("/refresh")
    fun refreshAccessToken(@RequestBody request: RefreshRequest): RefreshResponse? =
        authenticationService.refreshAccessToken(request)

}

