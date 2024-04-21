package com.example.crm.controller

import com.example.crm.annotation.CurrentUser
import com.example.crm.dto.OrderDTO
import com.example.crm.model.OrderStatus
import com.example.crm.security.MyUserPrincipal
import com.example.crm.service.OrderService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/order")
class  OrderController(
    private val orderService: OrderService
) {
    @GetMapping("/v1/all")
    fun getAllOrders(@PageableDefault(size = 5) pageable: Pageable): Page<OrderDTO> =
        orderService.getAllOrders(pageable)

    @GetMapping("/v1/user")
    fun getUserOrders(
        @CurrentUser user: MyUserPrincipal
    ): List<OrderDTO> = orderService.getUserOrders(user)

    @GetMapping("/v1/user/status")
    fun getUserOrdersByStatus(
        @CurrentUser user: MyUserPrincipal,
        @RequestParam("status") status: OrderStatus
    ): List<OrderDTO> = orderService.getUserOrdersByStatus(user, status)

    @GetMapping("/v1/get/{id}")
    fun getOrderById(@PathVariable id: UUID): OrderDTO =
        orderService.getOrderById(id)

    @PostMapping("/v1/save")
    fun saveOrder(
        @RequestBody orderDTO: OrderDTO,
        @CurrentUser user: MyUserPrincipal
    ): OrderDTO = orderService.saveOrder(orderDTO, user)

    @DeleteMapping("/v1/delete/{id}")
    fun deleteOrder(@PathVariable id: UUID) =
        orderService.deleteOrder(id)

    @PutMapping("/v1/update/{id}")
    fun updateOrder(@PathVariable id: UUID, @RequestBody orderDTO: OrderDTO) =
        orderService.updateOrder(id, orderDTO)
}
