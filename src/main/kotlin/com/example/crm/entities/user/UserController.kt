package com.example.crm.entities.user

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

    @GetMapping("/get")
    fun getAllUser(@PageableDefault(size = 5) pageable: Pageable): Page<UserDTO> =
        userService.getAllUser(pageable)

    @PostMapping("/save")
    fun saveUser(@RequestBody userDTO: UserDTO): UserDTO =
        userService.saveUser(userDTO)

    @DeleteMapping("/delete/{id}")
    fun deleteUser(@PathVariable id: UUID) =
        userService.deleteUser(id)

    @GetMapping("/get/{id}")
    fun getUserById(@PathVariable id: UUID): UserDTO =
        userService.getUserById(id)

    @PutMapping("/update/{id}")
    fun updateUser(@PathVariable id: UUID, @RequestBody userDTO: UserDTO) =
        userService.updateUser(id, userDTO)

}