package com.example.crm.service

import com.example.crm.data.entity.Customer
import com.example.crm.data.entity.User
import com.example.crm.data.repositories.UserRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
) {
    fun saveUser(user: User): User {
        return userRepository.save(user)
    }

    fun deleteUser(id: Long){
        if (!userRepository.existsById(id)) {
            throw NoSuchElementException("Customer with id $id not found")
        }
        return userRepository.deleteById(id)
    }

    @Cacheable("customersById", key = "#id")
    fun getUserById(id: Long): User {
        println("LOG: call getCustomerById")
        return userRepository.findById(id)
            .orElseThrow {
                NoSuchElementException("Customer with id $id not found")
            }
    }
}