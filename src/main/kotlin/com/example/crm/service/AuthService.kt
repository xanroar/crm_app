package com.example.crm.service

import com.example.crm.dto.*
import com.example.crm.model.RefreshToken
import com.example.crm.security.MyUserPrincipal
import com.example.crm.security.MyUserDetailsService
import com.example.crm.security.jwt.JwtTokenService
import com.example.crm.security.jwt.RefreshTokenService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthService(
    private val authManager: AuthenticationManager,
    private val userDetailsService: MyUserDetailsService,
    private val tokenService: JwtTokenService,
    private val refreshTokenService: RefreshTokenService,
    private val userService: UserService,
) {
    fun authentication(authenticationRequest: AuthRequest): AuthResponse {
        val user = authenticateUser(authenticationRequest)
        return generateTokens(user)
    }
    private fun authenticateUser(authenticationRequest: AuthRequest): MyUserPrincipal {
        authenticateCredentials(authenticationRequest)
        return loadUserDetails(authenticationRequest.email)
    }
    private fun authenticateCredentials(authenticationRequest: AuthRequest) {
        try {
            authManager.authenticate(UsernamePasswordAuthenticationToken(
                authenticationRequest.email, authenticationRequest.password))
        } catch (e: BadCredentialsException) {
            throw BadCredentialsException("Incorrect username or password")
        }
    }
    private fun loadUserDetails(email: String): MyUserPrincipal {
        return userDetailsService.loadUserByUsername(email)
    }
    private fun generateTokens(user: MyUserPrincipal): AuthResponse {
        val accessToken = tokenService.createAccessToken(user)
        val refreshToken = tokenService.createRefreshToken(user)
        val token = RefreshToken(
            userId = user.getId(),
            refreshToken = refreshToken,
            expiryDate = tokenService.getRefreshTokenExpiration().toInstant()
        )
        refreshTokenService.save(token)
        return AuthResponse(accessToken = accessToken, refreshToken = refreshToken)
    }
    fun deleteRefreshTokenByUserId(userId: UUID) {
        refreshTokenService.deleteByUserId(userId)
    }

    fun registerUser(user: UserDTO): UserDTO{
        return userService.saveUser(user)
    }

    fun refreshAccessToken(refreshToken: RefreshRequest): RefreshResponse {
        return refreshTokenService.processRefreshToken(refreshToken)
    }
}
