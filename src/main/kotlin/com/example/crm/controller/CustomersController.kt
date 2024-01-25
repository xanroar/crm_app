package com.example.crm.controller


import com.example.crm.data.entity.Customer
import com.example.crm.dto.CustomerDTO
import com.example.crm.service.CustomerMapperService
import com.example.crm.service.CustomersService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class CustomersController(
    private val customersService: CustomersService,
    private val mapperService: CustomerMapperService) {

    @PostMapping("/customers")
    fun saveCustomer(@RequestBody customerDTO: CustomerDTO): Customer = customersService.saveCustomer(mapperService.convertDtoToEntity(customerDTO))

    @DeleteMapping("/customers/{id}")
    fun deleteCustomer(@PathVariable id: Long) = customersService.deleteCustomer(id)

    @GetMapping("/customers/{id}")
    fun getCustomerById(@PathVariable id: Long): CustomerDTO = mapperService.convertEntityToDto(customersService.getCustomerById(id))


//    @PutMapping("/customers/{id}")
//    fun updateCustomer(@PathVariable id: Int, @RequestBody updatedCustomerDTO: CustomersDTO) = customersService.updateCustomer(id, updatedCustomerDTO.toEntity())


//    @GetMapping("/customers")
//    fun getAllCustomers(@RequestParam(defaultValue = "0") page: Int,
//                        @RequestParam(defaultValue = "5") size: Int): List<CustomersDTO> {
//        val customers = customersService.getAllCustomers(page, size)
//        return customers.map { it.toDTO() }
//    }





}
