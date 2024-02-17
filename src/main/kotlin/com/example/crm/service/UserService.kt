package com.example.crm.service

import com.example.crm.data.entity.User
import com.example.crm.data.repositories.UserRepository
import com.example.crm.dto.UserDTO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(
    private val userRepository: UserRepository,
    private val mapperService: MapperService) {

    fun getAllUser(pageable: Pageable): Page<UserDTO> {
        val customers = userRepository.findAll(pageable)

        return customers.map { mapperService.convertEntityToDto(it, UserDTO::class.java) }
    }

    fun saveUser(userDTO: UserDTO): UserDTO {
        val customer = mapperService.convertDtoToEntity(userDTO, User::class.java )

        val savedUser = userRepository.save(customer)

        return mapperService.convertEntityToDto (savedUser, UserDTO::class.java)
    }

    fun deleteUser(id: UUID){
        if (!userRepository.existsById(id)) {
            throw NoSuchElementException("Customer with id $id not found")
        }
        userRepository.deleteById(id)
    }

    fun getUserById(id: UUID): UserDTO {
        val user = userRepository.findById(id)
            .orElseThrow {
                NoSuchElementException("Customer with id $id not found")
            }
        return mapperService.convertEntityToDto(user, UserDTO::class.java)
    }

    fun updateUser(id: UUID, userDTO: UserDTO): UserDTO {
        val existingCustomer = userRepository.findById(id)
            .orElseThrow { NoSuchElementException("Customer with id $id not found") }

        val updatedUser = mapperService.convertDtoToEntity(userDTO, User::class.java)

        if (existingCustomer != null) {
            updatedUser.id = existingCustomer.id
        }

        val savedCustomer = userRepository.save(updatedUser)

        return mapperService.convertEntityToDto(savedCustomer, UserDTO::class.java)
    }
}
