package com.example.crm.repository

import com.example.crm.model.RefreshToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.stereotype.Repository
import java.util.*


@Repository
    interface RefreshTokenRepository : JpaRepository<RefreshToken, Long> {

        fun findByRefreshToken(token: String): RefreshToken
        @Modifying
        fun deleteByUserId(user: UUID): Int

    }
