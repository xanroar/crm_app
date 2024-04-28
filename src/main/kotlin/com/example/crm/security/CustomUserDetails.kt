package com.example.crm.security

import com.example.crm.domain.user.model.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.UUID

class CustomUserDetails(private val user: User): UserDetails {
    fun getId(): UUID = user.id

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return user.permissions.mapTo(mutableSetOf()) { permission ->
            SimpleGrantedAuthority(permission.name)
        }
    }

    override fun getPassword(): String = user.password

    override fun getUsername(): String = user.email

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}
