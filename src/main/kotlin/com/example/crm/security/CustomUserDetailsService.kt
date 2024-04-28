package com.example.crm.security

import com.example.crm.domain.user.UserRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {
    @Cacheable(value = ["users"], key = "#username")
    override fun loadUserByUsername(username: String): CustomUserDetails =
        userRepository.findByEmail(username)
            ?.orElseThrow { UsernameNotFoundException("Not found!") }
            ?.let { CustomUserDetails(it) }
            ?: throw UsernameNotFoundException("Not found!")

}