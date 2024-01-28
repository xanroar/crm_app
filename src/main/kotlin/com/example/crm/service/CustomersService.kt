package com.example.crm.service

import com.example.crm.data.entity.Customer
import com.example.crm.data.repositories.CustomersRepository
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class CustomersService(private val customersRepository: CustomersRepository) {

    fun getAllCustomers(page: Int, size: Int): List<Customer> {
        val pageable: Pageable = PageRequest.of(page, size, Sort.by("id"))

        val customersPage = customersRepository.findAll(pageable)
        return customersPage.content
    }
    fun saveCustomer(customer: Customer): Customer {
        return customersRepository.save(customer)
    }

    fun deleteCustomer(id: Long){
        if (!customersRepository.existsById(id)) {
            throw NoSuchElementException("Customer with id $id not found")
        }
        return customersRepository.deleteById(id)
    }

    @Cacheable("customersById", key = "#id")
    fun getCustomerById(id: Long): Customer {
        println("LOG: call getCustomerById")
        return customersRepository.findById(id)
            .orElseThrow {
                NoSuchElementException("Customer with id $id not found")
            }
    }

//    @CacheEvict("customersById", key = "#id")
//    fun updateCustomer(id: Long, updatedCustomer: Customer) {
//        if (!customersRepository.existsById(id)) {
//            throw NoSuchElementException("Customer with id $id not found")
//        }
//
//        val updatedCustomerWithId = updatedCustomer.copy(id = id)
//        customersRepository.save(updatedCustomerWithId)
//    }

//    fun getAllCustomers(page: Int, size: Int): List<Customer> {
//        val pageable: Pageable = PageRequest.of(page, size, Sort.by("id"))
//
//        val customersPage = customersRepository.findAll(pageable)
//        return customersPage.content
//    }

//
//
//


}
