package com.example.crm.service

import com.example.crm.dto.UserDTO
import com.example.crm.model.Permission
import com.example.crm.repository.PermissionRepository
import com.example.crm.model.Role
import com.example.crm.model.User
import com.example.crm.repository.RoleRepository
import com.example.crm.repository.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(
    private val userRepository: UserRepository,
    private val mapperService: MapperService,
    private val roleRepository: RoleRepository,
    private val permissionRepository: PermissionRepository,
) {

    fun getAllUser(pageable: Pageable): Page<UserDTO> {
        val users = userRepository.findAll(pageable)
        return users.map { mapperService.convertEntityToDto(it, UserDTO::class.java) }
    }

    fun saveUser(userDTO: UserDTO): UserDTO {

        if(doesUserExistInDatabase(userDTO.email))
            throw IllegalArgumentException("User with email ${userDTO.email} already exists.")

        val user = createUserFromDto(userDTO)

        val savedUser = userRepository.save(user)
        return mapperService.convertEntityToDto(savedUser, UserDTO::class.java)
    }
    fun doesUserExistInDatabase(email: String?): Boolean {
        return userRepository.findByEmail(email)?.isPresent ?: false
    }
    private fun createUserFromDto(userDTO: UserDTO): User {
        val user = User()
        user.id = userDTO.id ?: UUID.randomUUID()
        user.email = userDTO.email
        user.password = encodePassword(userDTO.password)
        user.role = findRole(userDTO.role?.name)
        user.permissions = findPermissions(userDTO.permissions)
        return user
    }
    private fun encodePassword(password: String?): String {
        return BCryptPasswordEncoder().encode(password)
    }
    private fun findRole(roleName: String?): Role? {
        return roleRepository.findByName(roleName)?.orElse(null)
    }
    private fun findPermissions(permissionNames: Set<Permission>?): Set<Permission> {
        val permissionNamesAsString = permissionNames?.map { it.name }?.toSet()
        return permissionRepository.findByNameIn(permissionNamesAsString).filterNotNull().toSet()
    }

    fun deleteUser(id: UUID){
        if (!userRepository.existsById(id)) {
            throw NoSuchElementException("User with id $id not found")
        }
        userRepository.deleteById(id)
    }

    fun getUserById(id: UUID): UserDTO {
        val user = userRepository.findById(id)
            .orElseThrow {
                NoSuchElementException("User with id $id not found")
            }
        return mapperService.convertEntityToDto(user, UserDTO::class.java)
    }
    fun updateUser(id: UUID, userDTO: UserDTO): UserDTO {
        val existingCustomer = userRepository.findById(id)
            .orElseThrow { NoSuchElementException("User with id $id not found") }

        val updatedUser = mapperService.convertDtoToEntity(userDTO, User::class.java)

        if (existingCustomer != null)
            updatedUser.id = existingCustomer.id

        val savedCustomer = userRepository.save(updatedUser)

        return mapperService.convertEntityToDto(savedCustomer, UserDTO::class.java)
    }
}
