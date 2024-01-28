package com.example.crm.controller

import com.example.crm.data.entity.User
import com.example.crm.dto.UserDTO
import com.example.crm.service.CustomerMapperService
import com.example.crm.service.UserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController(
    private val userService: UserService,
    private val mapperService: CustomerMapperService
) {
    @PostMapping("/customers")
    fun saveCustomer(@RequestBody userDTO: UserDTO): User = userService.saveUser(mapperService.convertDtoToEntity(userDTO, User::class.java))

    @DeleteMapping("/customers/{id}")
    fun deleteCustomer(@PathVariable id: Long) = userService.deleteUser(id)

    @GetMapping("/customers/{id}")
    fun getCustomerById(@PathVariable id: Long): UserDTO = mapperService.convertEntityToDto(userService.getUserById(id), UserDTO::class.java)



}