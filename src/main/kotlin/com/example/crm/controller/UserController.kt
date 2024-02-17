package com.example.crm.controller

import com.example.crm.dto.UserDTO
import com.example.crm.service.UserService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api")
class UserController(
    private val userService: UserService) {

    @GetMapping("/users")
    fun getAllUser(@PageableDefault(size = 5) pageable: Pageable): Page<UserDTO> =
        userService.getAllUser(pageable)

    @PostMapping("/user")
    fun saveUser(@RequestBody userDTO: UserDTO): UserDTO =
        userService.saveUser(userDTO)

    @DeleteMapping("/user/{id}")
    fun deleteUser(@PathVariable id: UUID) =
        userService.deleteUser(id)

    @GetMapping("/user/{id}")
    fun getUserById(@PathVariable id: UUID): UserDTO =
        userService.getUserById(id)

    @PutMapping("/user/{id}")
    fun updateUser(@PathVariable id: UUID, @RequestBody userDTO: UserDTO) =
        userService.updateUser(id, userDTO)

}