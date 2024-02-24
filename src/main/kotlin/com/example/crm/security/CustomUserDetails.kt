package com.example.crm.security

import com.example.crm.entities.user.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.UUID

class CustomUserDetails(private val user: User): UserDetails {
    fun getId(): UUID = user.id

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val authorities = mutableListOf<GrantedAuthority>()

        user.role?.let {
            authorities.add(SimpleGrantedAuthority(it.name))
        }

        user.permissions.mapTo(authorities) {
            SimpleGrantedAuthority(it.name)
        }

        return authorities
    }

    override fun getPassword(): String? = user.password

    override fun getUsername(): String? = user.email

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}
