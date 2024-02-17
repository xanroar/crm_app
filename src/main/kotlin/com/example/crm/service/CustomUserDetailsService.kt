package com.example.crm.service

import com.example.crm.data.repositories.UserRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

typealias ApplicationUser = com.example.crm.data.entity.User
@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails =
        userRepository.findByEmail(username)
            ?.orElseThrow { UsernameNotFoundException("Not found!") }
            ?.mapToUserDetails()
            ?: throw UsernameNotFoundException("Not found!")


    private fun ApplicationUser.mapToUserDetails(): UserDetails =
        User.builder()
            .username(this.email)
            .password(this.password)
          //  .roles(this.roles.name)
            .build()
}