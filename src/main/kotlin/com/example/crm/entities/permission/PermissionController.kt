package com.example.crm.entities.permission

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
    @GetMapping("/get")
    fun getAllPermission(@PageableDefault(size = 5) pageable: Pageable): Page<Permission?> =
        permissionService.getAllPermission(pageable)

    @PostMapping("/save")
    fun savePermission(@RequestBody permission: Permission): Permission =
        permissionService.savePermission(permission)

    @DeleteMapping("/delete/{id}")
    fun deletePermission(@PathVariable id: UUID) =
        permissionService.deletePermission(id)

    @GetMapping("/get/{id}")
    fun getPermissionById(@PathVariable id: UUID): Permission =
        permissionService.getPermissionById(id)

}
