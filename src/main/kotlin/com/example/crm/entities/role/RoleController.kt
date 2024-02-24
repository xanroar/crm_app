package com.example.crm.entities.role

import com.example.crm.entities.permission.Permission
import com.example.crm.entities.permission.PermissionService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("/api/role")
class RoleController(
    private val roleService: RoleService
) {
    @GetMapping("/get")
    fun getAllRole(@PageableDefault(size = 5) pageable: Pageable): Page<Role?> =
        roleService.getAllRole(pageable)

    @PostMapping("/save")
    fun saveRole(@RequestBody role: Role): Role =
        roleService.saveRole(role)

    @DeleteMapping("/delete/{id}")
    fun deleteRole(@PathVariable id: UUID) =
        roleService.deleteRole(id)

    @GetMapping("/get/{id}")
    fun getRoleById(@PathVariable id: UUID): Role =
        roleService.getRoleById(id)

}
