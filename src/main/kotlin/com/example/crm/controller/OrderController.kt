package com.example.crm.controller

import com.example.crm.dto.OrderDTO
import com.example.crm.service.OrderService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/order")
class OrderController(
    private val orderService: OrderService
) {
    @GetMapping("/get")
    fun getAllOrders(@PageableDefault(size = 5) pageable: Pageable): Page<OrderDTO> =
        orderService.getAllOrders(pageable)
    @GetMapping("/get/{id}")
    fun getOrderById(@PathVariable id: UUID): OrderDTO =
        orderService.getOrderById(id)

    @PostMapping("/save")
    fun saveOrder(@RequestBody orderDTO: OrderDTO): OrderDTO =
        orderService.saveOrder(orderDTO)

    @DeleteMapping("/delete/{id}")
    fun deleteOrder(@PathVariable id: UUID) =
        orderService.deleteOrder(id)

    @PutMapping("/update/{id}")
    fun updateOrder(@PathVariable id: UUID, @RequestBody orderDTO: OrderDTO) =
        orderService.updateOrder(id, orderDTO)
}
