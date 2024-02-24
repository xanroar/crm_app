package com.example.crm.entities.order

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api")
class OrderController(
    private val orderService: OrderService
) {
    @GetMapping("/orders/getAll")
    fun getAllOrders(@PageableDefault(size = 5) pageable: Pageable): Page<OrderDTO> =
        orderService.getAllOrders(pageable)

    @PostMapping("/order")
    fun saveOrder(@RequestBody orderDTO: OrderDTO): OrderDTO =
        orderService.saveOrder(orderDTO)

    @DeleteMapping("/order/{id}")
    fun deleteOrder(@PathVariable id: UUID) =
        orderService.deleteOrder(id)

    @GetMapping("/order/{id}")
    fun getOrderById(@PathVariable id: UUID): OrderDTO =
        orderService.getOrderById(id)

    @PutMapping("/orders/{id}")
    fun updateOrder(@PathVariable id: UUID, @RequestBody orderDTO: OrderDTO) =
        orderService.updateOrder(id, orderDTO)
}
