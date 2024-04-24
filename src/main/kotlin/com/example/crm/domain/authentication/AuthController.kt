package com.example.crm.domain.authentication

import com.example.crm.domain.authentication.jwt.token.TokenRequest
import com.example.crm.domain.authentication.jwt.token.TokenResponse
import com.example.crm.domain.authentication.jwt.token.refresh.RefreshResponse
import com.example.crm.domain.user.model.UserDTO
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/auth")
class AuthController(
    private val authenticationService: AuthService
) {
    @PostMapping("/login")
    fun authenticate(@RequestBody authRequest: TokenRequest): TokenResponse =
        authenticationService.authentication(authRequest)

    @PostMapping("/register")
    fun registerUser(@RequestBody userDTO: UserDTO): UserDTO =
        authenticationService.registerUser(userDTO)

    @PostMapping("/logout")
    fun logoutUser(@RequestHeader(value="Authorization")user: String) =
        authenticationService.deleteRefreshTokenByUserId(user)

    @PostMapping("/refresh")
    fun refreshAccessToken(@RequestHeader(value="Authorization")refreshToken: String): RefreshResponse? =
        authenticationService.refreshAccessToken(refreshToken)
}


