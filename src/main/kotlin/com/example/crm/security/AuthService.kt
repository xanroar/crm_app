package com.example.crm.security

import com.example.crm.security.jwt.JwtProperties
import com.example.crm.security.authTokens.AuthRequest
import com.example.crm.security.authTokens.AuthResponse
import com.example.crm.security.authTokens.RefreshRequest
import com.example.crm.security.authTokens.RefreshResponse
import com.example.crm.security.jwt.RefreshTokenRepository
import com.example.crm.security.jwt.TokenService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthService(
    private val authManager: AuthenticationManager,
    private val userDetailsService: CustomUserDetailsService,
    private val tokenService: TokenService,
    private val jwtProperties: JwtProperties,
    private val refreshTokenRepository: RefreshTokenRepository,
) {

    fun authentication(authenticationRequest: AuthRequest): AuthResponse {
        val user = getUser(authenticationRequest)
        val accessToken = createAccessToken(user)
        val refreshToken = createRefreshToken(user)

        refreshTokenRepository.save(refreshToken, user)
        return AuthResponse(accessToken = accessToken, refreshToken = refreshToken)
    }

    private fun getUser(authenticationRequest: AuthRequest): UserDetails {
        try {
            authManager.authenticate(UsernamePasswordAuthenticationToken(
                authenticationRequest.email, authenticationRequest.password))

            return userDetailsService.loadUserByUsername(authenticationRequest.email)
        } catch (e: BadCredentialsException) {
            throw BadCredentialsException("Incorrect username or password")
        }
    }

    fun refreshAccessToken(refreshToken: RefreshRequest): RefreshResponse {
        val extractedEmail = tokenService.extractEmail(refreshToken.token) ?:
        throw IllegalArgumentException("Invalid token")

        val currentUserDetails = userDetailsService.loadUserByUsername(extractedEmail)
        val refreshTokenUserDetails = refreshTokenRepository.findUserDetailsByToken(refreshToken.token)

        if (isTokenValid(refreshToken.token, currentUserDetails, refreshTokenUserDetails)) {
            return RefreshResponse(createAccessToken(currentUserDetails))
        } else {
            throw IllegalStateException("Token is expired or user details do not match")
        }
    }

    private fun isTokenValid(token: String, currentUserDetails: UserDetails, refreshTokenUserDetails: UserDetails?): Boolean {
        return !tokenService.isExpired(token) && refreshTokenUserDetails?.username == currentUserDetails.username
    }

    private fun createAccessToken(user: UserDetails) = tokenService.generate(
        userDetails = user,
        expirationDate = getAccessTokenExpiration()
    )

    private fun createRefreshToken(user: UserDetails) = tokenService.generate(
        userDetails = user,
        expirationDate = getRefreshTokenExpiration()
    )

    private fun getAccessTokenExpiration(): Date =
        Date(System.currentTimeMillis() + jwtProperties.accessTokenExpiration)

    private fun getRefreshTokenExpiration(): Date =
        Date(System.currentTimeMillis() + jwtProperties.refreshTokenExpiration)
}