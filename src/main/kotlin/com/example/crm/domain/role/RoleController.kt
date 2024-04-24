package com.example.crm.domain.role

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("/v1/roles")
class RoleController(
    private val roleService: RoleService
) {
    @GetMapping
    fun getAllRoles(@PageableDefault(size = 5) pageable: Pageable): Page<Role?> =
        roleService.getAllRoles(pageable)

    @PostMapping
    fun saveRole(@RequestBody role: Role): Role =
        roleService.saveRole(role)

    @DeleteMapping("/{id}")
    fun deleteRole(@PathVariable id: UUID) =
        roleService.deleteRole(id)
}
