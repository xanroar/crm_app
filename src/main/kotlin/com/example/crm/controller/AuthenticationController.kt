package com.example.crm.controller

import com.example.crm.dto.AuthRequest
import com.example.crm.dto.AuthResponse
import com.example.crm.dto.RefreshRequest
import com.example.crm.dto.RefreshResponse
import com.example.crm.service.AuthenticationService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class AuthenticationController(
    private val authenticationService: AuthenticationService) {

    @PostMapping("/auth")
    fun authenticate(@RequestBody authRequest: AuthRequest): AuthResponse =
        authenticationService.authentication(authRequest)

    @PostMapping("/refresh")
    fun refreshAccessToken(@RequestBody request: RefreshRequest): RefreshResponse? =
        authenticationService.refreshAccessToken(request)

}

