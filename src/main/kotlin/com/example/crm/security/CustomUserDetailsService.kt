package com.example.crm.security

import com.example.crm.entities.permission.Permission
import com.example.crm.entities.role.Role
import com.example.crm.entities.user.UserRepository
import com.example.crm.service.ApplicationUser
import org.springframework.cache.annotation.Cacheable
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {


    @Cacheable(value = ["users"], key = "#username")
    override fun loadUserByUsername(username: String): UserDetails =
        userRepository.findByEmail(username)
            ?.orElseThrow { UsernameNotFoundException("Not found!") }
            ?.let { CustomUserDetails(it) }
            ?: throw UsernameNotFoundException("Not found!")


    private fun ApplicationUser.mapToUserDetails(): UserDetails =
        User.builder()
            .username(this.email)
            .password(this.password)
            .authorities(getAuthorities(this.role, this.permissions))
            .build()
    private fun getAuthorities(role: Role?, permissions: Set<Permission>): List<GrantedAuthority> {
        val authorities = mutableListOf<GrantedAuthority>()

        if (role != null) {
            authorities.add(SimpleGrantedAuthority(role.name))
        }
        permissions.forEach { permission ->
            authorities.add(SimpleGrantedAuthority(permission.name))
        }
        return authorities
    }
}