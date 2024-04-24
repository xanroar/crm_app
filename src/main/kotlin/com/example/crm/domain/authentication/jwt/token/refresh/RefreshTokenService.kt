package com.example.crm.domain.authentication.jwt.token.refresh

import com.example.crm.domain.authentication.jwt.JwtTokenService
import com.example.crm.security.MyUserDetailsService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*


@Service
class RefreshTokenService (
    private val tokenService: JwtTokenService,
    private val userDetailsService: MyUserDetailsService,
    private val refreshTokenRepository: RefreshTokenRepository,
    ){
    fun findByRefreshToken(token: String?): RefreshToken {
        return refreshTokenRepository.findByRefreshToken(token!!)
    }
    fun save(token: RefreshToken): RefreshToken {
        return refreshTokenRepository.save(token)
    }
    @Transactional
    fun deleteByUserId(userId: UUID): Int {
        return refreshTokenRepository.deleteByUserId(userId)
    }
    fun processRefreshToken(refreshToken: RefreshRequest): RefreshResponse {
        val extractedEmail = tokenService.extractEmail(refreshToken.token) ?:
        throw IllegalArgumentException("Invalid token")

        val currentUserDetails = userDetailsService.loadUserByUsername(extractedEmail)
        val refreshTokenUserDetails = findByRefreshToken(refreshToken.token)

        if (isTokenValid(refreshToken.token, currentUserDetails.getId(), refreshTokenUserDetails.userId)) {
            return RefreshResponse(tokenService.createAccessToken(currentUserDetails))
        } else {
            throw IllegalStateException("Token is expired or user details do not match")
        }
    }
    private fun isTokenValid(token: String, currentUserDetails: UUID, refreshTokenUserDetails: UUID?): Boolean {
        return !tokenService.isExpired(token) && refreshTokenUserDetails == currentUserDetails
    }


}
