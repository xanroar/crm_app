package com.example.crm.annotation

import org.springframework.security.core.annotation.AuthenticationPrincipal

@AuthenticationPrincipal
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class CurrentUser
