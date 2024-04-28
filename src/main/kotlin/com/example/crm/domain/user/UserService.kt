package com.example.crm.domain.user

import com.example.crm.domain.user.model.User
import com.example.crm.domain.user.model.UserDTO
import com.example.crm.domain.permission.Permission
import com.example.crm.domain.permission.PermissionRepository
import com.example.crm.domain.role.RoleRepository
import com.example.crm.lib.MapperService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class UserService(
    private val userRepository: UserRepository,
    private val mapperService: MapperService,
    private val roleRepository: RoleRepository,
    private val permissionRepository: PermissionRepository,
) {
    fun getAllUsers(pageable: Pageable): Page<UserDTO> {
        val users = userRepository.findAll(pageable)
        return users.map { mapperService.convertEntityToDto(it, UserDTO::class.java) }
    }

    fun saveUser(userDTO: UserDTO): UserDTO {
        if(doesUserExistInDatabase(userDTO.email))
            throw IllegalArgumentException("User with email ${userDTO.email} already exists.")

        val user = createUserFromDto(userDTO)

        userRepository.save(user)

        return convertEntityToDto(user)
    }
    fun doesUserExistInDatabase(email: String?): Boolean {
        return userRepository.findByEmail(email)?.isPresent ?: false
    }
    private fun convertEntityToDto(user: User): UserDTO {
        return UserDTO(
            email = user.email,
            password = user.password,
            permissions = user.permissions
        )
    }
    private fun createUserFromDto(userDTO: UserDTO): User {
        return User(
            email = userDTO.email,
            password = encodePassword(userDTO.password),
            permissions = findPermissions(userDTO)
        ).apply {
            createdAt = LocalDateTime.now()
        }
    }
    private fun encodePassword(password: String?): String {
        return BCryptPasswordEncoder().encode(password)
    }

    private fun findPermissions(userDTO: UserDTO): Set<Permission> {
        val roles = userDTO.role.map { it.name } .toSet()
        val permissions = userDTO.permissions.map { it.name }

        val permissionsFromRoles = roleRepository.findByNameIn(roles)
            .flatMap { it?.permissions.orEmpty() }
            .map { it.name }
            .toSet()

        val allPermissionNames = permissionsFromRoles + permissions

        return permissionRepository.findByNameIn(allPermissionNames).filterNotNull().toSet()
    }

    fun deleteUser(id: UUID){
        if (!userRepository.existsById(id)) {
            throw NoSuchElementException("User with id $id not found")
        }
        userRepository.deleteById(id)
    }

    fun findUserById(id: UUID): User {
        return userRepository.findById(id)
            .orElseThrow {
                NoSuchElementException("User with id $id not found")
            }!!
    }

    fun getUserById(id: UUID): UserDTO {
        val user = findUserById(id)
        return mapperService.convertEntityToDto(user, UserDTO::class.java)
    }
    fun updateUser(id: UUID, userDTO: UserDTO): UserDTO {
        val existingUser = userRepository.findById(id)
            .orElseThrow { NoSuchElementException("User with id $id not found") }

        val updatedUser = existingUser?.apply {
            email = userDTO.email
            password = encodePassword(userDTO.password)
            permissions = findPermissions(userDTO)
            updatedAt = LocalDateTime.now()
        }

        val savedUser = userRepository.save(updatedUser!!)

        return convertEntityToDto(savedUser)
    }

}
