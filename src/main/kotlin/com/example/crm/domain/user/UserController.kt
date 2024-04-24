package com.example.crm.domain.user

import com.example.crm.annotation.CurrentUser
import com.example.crm.domain.user.model.UserDTO
import com.example.crm.security.MyUserPrincipal
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/v1/users")
class UserController(
    private val userService: UserService
) {
    //    @PreAuthorize("hasAuthority('READ')")
    @GetMapping
    fun getAllUsers(@PageableDefault(size = 5) pageable: Pageable): Page<UserDTO> =
        userService.getAllUsers(pageable)

    @GetMapping("/current")
    fun getCurrentUser(@CurrentUser user: MyUserPrincipal ): UserDTO =
        userService.getUserById(user.getId())

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: UUID) =
        userService.deleteUser(id)

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: UUID): UserDTO =
        userService.getUserById(id)

    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: UUID, @RequestBody userDTO: UserDTO) =
        userService.updateUser(id, userDTO)
}

