package com.example.crm.domain.authentication

import com.example.crm.domain.authentication.jwt.token.TokenRequest
import com.example.crm.domain.authentication.jwt.token.TokenResponse
import com.example.crm.domain.authentication.jwt.token.refresh.RefreshRequest
import com.example.crm.domain.authentication.jwt.token.refresh.RefreshResponse
import com.example.crm.domain.user.model.UserDTO
import com.example.crm.domain.authentication.jwt.token.refresh.RefreshToken
import com.example.crm.domain.user.UserRepository
import com.example.crm.domain.user.UserService
import com.example.crm.security.CustomUserDetails
import com.example.crm.security.CustomUserDetailsService
import com.example.crm.domain.authentication.jwt.JwtTokenService
import com.example.crm.domain.authentication.jwt.token.refresh.RefreshTokenService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val authManager: AuthenticationManager,
    private val userDetailsService: CustomUserDetailsService,
    private val tokenService: JwtTokenService,
    private val refreshTokenService: RefreshTokenService,
    private val userService: UserService,
    private val userRepository: UserRepository,
) {
    fun authentication(authenticationRequest: TokenRequest): TokenResponse {
        val user = authenticateUser(authenticationRequest)
        return generateTokens(user)
    }
    private fun authenticateUser(authenticationRequest: TokenRequest): CustomUserDetails {
        authenticateCredentials(authenticationRequest)
        return loadUserDetails(authenticationRequest.email)
    }
    private fun authenticateCredentials(authenticationRequest: TokenRequest) {
        try {
            authManager.authenticate(UsernamePasswordAuthenticationToken(
                authenticationRequest.email, authenticationRequest.password))
        } catch (e: BadCredentialsException) {
            throw BadCredentialsException("Incorrect username or password")
        }
    }
    private fun loadUserDetails(email: String): CustomUserDetails {
        return userDetailsService.loadUserByUsername(email)
    }
    private fun generateTokens(user: CustomUserDetails): TokenResponse {
        val accessToken = tokenService.createAccessToken(user)
        val refreshToken = tokenService.createRefreshToken(user)
        val token = RefreshToken(
            userId = user.getId(),
            refreshToken = refreshToken,
            expiryDate = tokenService.getRefreshTokenExpiration().toInstant()
        )
        refreshTokenService.save(token)
        return TokenResponse(accessToken = accessToken, refreshToken = refreshToken)
    }
    fun deleteRefreshTokenByUserId(token: String) {
        val email = tokenService.extractEmail(
            extractAccessToken(token))

        userRepository.findByEmail(email)?.ifPresent { user ->
            user.let { refreshTokenService.deleteByUserId(it.id) }
        }
    }
    fun registerUser(user: UserDTO): UserDTO {
        return userService.saveUser(user)
    }
    fun refreshAccessToken(refreshToken: String): RefreshResponse {
        val request = RefreshRequest(
            extractAccessToken(refreshToken))

        return refreshTokenService.processRefreshToken(request)
    }
    private fun extractAccessToken(token: String): String {
        return token.removePrefix("Bearer ").trim()
    }
}
