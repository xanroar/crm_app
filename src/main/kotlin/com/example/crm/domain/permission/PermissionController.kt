package com.example.crm.domain.permission

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/v1/permissions")
class PermissionController(
    private val permissionService: PermissionService
) {
    @GetMapping
    fun getAllPermissions(@PageableDefault(size = 5) pageable: Pageable): Page<Permission?> =
        permissionService.getAllPermissions(pageable)

    @PostMapping
    fun savePermission(@RequestBody permission: Permission): Permission =
        permissionService.savePermission(permission)

    @DeleteMapping("/{id}")
    fun deletePermission(@PathVariable id: UUID) =
        permissionService.deletePermission(id)
}
