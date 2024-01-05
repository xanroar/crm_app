package com.example.crm.service

import com.example.crm.data.model.Customers
import com.example.crm.data.repositories.CustomersRepository
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.data.domain.Sort

@Service
class CustomersService(private val customersRepository: CustomersRepository) {
    fun getAllCustomers(page: Int, size: Int): List<Customers> {
        val pageable: Pageable = PageRequest.of(page, size, Sort.by("id"))

        val customersPage = customersRepository.findAll(pageable)
        return customersPage.content
    }
    fun saveCustomer(customers: Customers): Customers {
        if (customers.run { name.isBlank() || email.isBlank() || phone.isBlank() }) {
            throw IllegalArgumentException("Invalid customer data. Name, email, and phone are required.")
        }
        return customersRepository.save(customers)
    }

    fun deleteCustomer(id: Int){
        if (!customersRepository.existsById(id)) {
            throw NoSuchElementException("Customer with id $id not found")
        }
        return customersRepository.deleteById(id)
    }

    @Cacheable("customersById", key = "#id")
    fun getCustomerById(id: Int): Customers {
        println("LOG: call getCustomerById")
        return customersRepository.findById(id)
            .orElseThrow {
                NoSuchElementException("Customer with id $id not found")
            }
    }

    @CacheEvict("customersById", key = "#id")
    fun updateCustomer(id: Int, updatedCustomer: Customers) {
        if (!customersRepository.existsById(id)) {
            throw NoSuchElementException("Customer with id $id not found")
        }

        val updatedCustomerWithId = updatedCustomer.copy(id = id)
        customersRepository.save(updatedCustomerWithId)
    }

}
