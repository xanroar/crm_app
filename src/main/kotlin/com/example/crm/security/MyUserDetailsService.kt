package com.example.crm.security

import com.example.crm.repository.UserRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class MyUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {
    @Cacheable(value = ["users"], key = "#username")
    override fun loadUserByUsername(username: String): MyUserPrincipal =
        userRepository.findByEmail(username)
            ?.orElseThrow { UsernameNotFoundException("Not found!") }
            ?.let { MyUserPrincipal(it) }
            ?: throw UsernameNotFoundException("Not found!")

}