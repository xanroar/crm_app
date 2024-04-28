package com.example.crm.domain.order

import com.example.crm.annotation.CurrentUser
import com.example.crm.domain.order.model.OrderDTO
import com.example.crm.domain.order.model.OrderStatus
import com.example.crm.security.CustomUserDetails
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/v1/orders")
class OrderController(
    private val orderService: OrderService
) {
    @GetMapping
    fun getAllOrders(@PageableDefault(size = 5) pageable: Pageable): Page<OrderDTO> =
        orderService.getAllOrders(pageable)

    @GetMapping("/user")
    fun getUserOrders(
        @CurrentUser user: CustomUserDetails
    ): List<OrderDTO> = orderService.getUserOrders(user)

    @GetMapping("/user/status")
    fun getUserOrdersByStatus(
        @CurrentUser user: CustomUserDetails,
        @RequestParam("status") status: OrderStatus
    ): List<OrderDTO> = orderService.getUserOrdersByStatus(user, status)

    @GetMapping("/{id}")
    fun getOrderById(@PathVariable id: UUID): OrderDTO =
        orderService.getOrderById(id)

    @PostMapping
    fun saveOrder(
        @RequestBody orderDTO: OrderDTO,
        @CurrentUser user: CustomUserDetails
    ): OrderDTO = orderService.saveOrder(orderDTO, user)

    @DeleteMapping("/{id}")
    fun deleteOrder(@PathVariable id: UUID) =
        orderService.deleteOrder(id)

    @PatchMapping("/{id}")
    fun updateOrder(@PathVariable id: UUID, @RequestBody orderDTO: OrderDTO) =
        orderService.updateOrder(id, orderDTO)
}

