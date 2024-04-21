package com.example.crm.controller

import com.example.crm.model.Permission
import com.example.crm.service.PermissionService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/permission")
class PermissionController(
    private val permissionService: PermissionService
) {
    @GetMapping("/v1/get")
    fun getAllPermission(@PageableDefault(size = 5) pageable: Pageable): Page<Permission?> =
        permissionService.getAllPermission(pageable)

    @PostMapping("/v1/save")
    fun savePermission(@RequestBody permission: Permission): Permission =
        permissionService.savePermission(permission)

    @DeleteMapping("/v1/delete/{id}")
    fun deletePermission(@PathVariable id: UUID) =
        permissionService.deletePermission(id)
}
