package com.example.crm.controller

import com.example.crm.annotation.CurrentUser
import com.example.crm.dto.UserDTO
import com.example.crm.security.MyUserPrincipal
import com.example.crm.service.UserService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/user")
class UserController(
    private val userService: UserService
) {
    //    @PreAuthorize("hasAuthority('READ')")
    @GetMapping("/v1/get")
    fun getAllUser(@PageableDefault(size = 5) pageable: Pageable): Page<UserDTO> =
        userService.getAllUser(pageable)

    @GetMapping("/v1/current")
    fun getCurrentUser(@CurrentUser user: MyUserPrincipal ): UserDTO =
        userService.getUserById(user.getId())

    @DeleteMapping("/v1/delete/{id}")
    fun deleteUser(@PathVariable id: UUID) =
        userService.deleteUser(id)

    @GetMapping("/v1/get/{id}")
    fun getUserById(@PathVariable id: UUID): UserDTO =
        userService.getUserById(id)

    @PutMapping("/v1/update/{id}")
    fun updateUser(@PathVariable id: UUID, @RequestBody userDTO: UserDTO) =
        userService.updateUser(id, userDTO)
}

