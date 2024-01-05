package com.example.crm.controller


import com.example.crm.data.model.CustomersDTO
import com.example.crm.service.CustomersService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class CustomersController(private val customersService: CustomersService) {
    @GetMapping("/customers")
    fun getAllCustomers(@RequestParam(defaultValue = "0") page: Int,
                        @RequestParam(defaultValue = "5") size: Int): List<CustomersDTO> {
        val customers = customersService.getAllCustomers(page, size)
        return customers.map { it.toDTO() }
    }
    @PostMapping("/customers")
    fun saveCustomer(@RequestBody customerDTO: CustomersDTO): CustomersDTO = customersService.saveCustomer(customerDTO.toEntity()).toDTO()

    @DeleteMapping("/customers/{id}")
    fun deleteCustomer(@PathVariable id: Int) = customersService.deleteCustomer(id)

    @PutMapping("/customers/{id}")
    fun updateCustomer(@PathVariable id: Int, @RequestBody updatedCustomerDTO: CustomersDTO) = customersService.updateCustomer(id, updatedCustomerDTO.toEntity())

    @GetMapping("/customers/{id}")
    fun getCustomerById(@PathVariable id: Int): CustomersDTO  = customersService.getCustomerById(id).toDTO()

}
