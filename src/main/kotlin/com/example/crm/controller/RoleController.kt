package com.example.crm.controller

import com.example.crm.model.Role
import com.example.crm.service.RoleService
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
    @GetMapping("/v1/get")
    fun getAllRole(@PageableDefault(size = 5) pageable: Pageable): Page<Role?> =
        roleService.getAllRole(pageable)

    @PostMapping("/v1/save")
    fun saveRole(@RequestBody role: Role): Role =
        roleService.saveRole(role)

    @DeleteMapping("/v1/delete/{id}")
    fun deleteRole(@PathVariable id: UUID) =
        roleService.deleteRole(id)

}
