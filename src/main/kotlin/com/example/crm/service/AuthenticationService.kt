package com.example.crm.service

import com.example.crm.security.JwtProperties
import com.example.crm.data.repositories.RefreshTokenRepository
import com.example.crm.dto.AuthRequest
import com.example.crm.dto.AuthResponse
import com.example.crm.dto.RefreshRequest
import com.example.crm.dto.RefreshResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthenticationService(
    private val authManager: AuthenticationManager,
    private val userDetailsService: CustomUserDetailsService,
    private val tokenService: TokenService,
    private val jwtProperties: JwtProperties,
    private val refreshTokenRepository: RefreshTokenRepository,
) {

    fun authentication(authenticationRequest: AuthRequest): AuthResponse {

        try {
            authManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    authenticationRequest.email,
                    authenticationRequest.password
                )
            )
        } catch (e: BadCredentialsException) {
            //TODO WARN 19160 --- [nio-8080-exec-7] o.s.s.c.bcrypt.BCryptPasswordEncoder     : Encoded password does not look like BCrypt
            //ResponseEntity(AppError(HttpStatus.UNAUTHORIZED.value(), "Неправильный логин или пароль"), HttpStatus.UNAUTHORIZED)
        }

        val user = userDetailsService.loadUserByUsername(authenticationRequest.email)

        val accessToken = createAccessToken(user)
        val refreshToken = createRefreshToken(user)

        refreshTokenRepository.save(refreshToken, user)

        return AuthResponse(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }
    fun refreshAccessToken(refreshToken: RefreshRequest): RefreshResponse? {
        val extractedEmail = tokenService.extractEmail(refreshToken.token)

        return RefreshResponse(extractedEmail?.let { email ->
            val currentUserDetails = userDetailsService.loadUserByUsername(email)
            val refreshTokenUserDetails = refreshTokenRepository.findUserDetailsByToken(refreshToken.token)

            if (!tokenService.isExpired(refreshToken.token) && refreshTokenUserDetails?.username == currentUserDetails.username)
                createAccessToken(currentUserDetails)
            //TODO add an exception
            else
                null
        })
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