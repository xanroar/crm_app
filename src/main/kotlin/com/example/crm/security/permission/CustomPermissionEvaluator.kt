package com.example.crm.security.permission

import org.springframework.security.access.PermissionEvaluator
import org.springframework.security.core.Authentication
import java.io.Serializable

class CustomPermissionEvaluator : PermissionEvaluator {

    override fun hasPermission(auth: Authentication?, targetDomainObject: Any?, permission: Any?): Boolean {
        if (auth == null || targetDomainObject == null || permission !is String) {
            return false
        }
        val targetType = targetDomainObject.javaClass.simpleName.uppercase()
        return hasPrivilege(auth, targetType, permission.toString().uppercase())
    }

    override fun hasPermission(auth: Authentication?, targetId: Serializable?, targetType: String?, permission: Any?): Boolean {
        if (auth == null || targetType == null || permission !is String) {
            return false
        }
        return hasPrivilege(auth, targetType.uppercase(), permission.toString().uppercase())
    }

    private fun hasPrivilege(auth: Authentication, targetType: String, permission: String): Boolean {
        for (grantedAuth in auth.authorities) {
            if (grantedAuth.authority.startsWith(targetType) && grantedAuth.authority.contains(permission)) {
                return true
            }
        }
        return false
    }
}
