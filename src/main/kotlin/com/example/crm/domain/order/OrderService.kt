package com.example.crm.domain.order

import com.example.crm.domain.order.model.Order
import com.example.crm.domain.order.model.OrderDTO
import com.example.crm.domain.order.model.OrderStatus
import com.example.crm.security.CustomUserDetails
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import com.example.crm.domain.user.UserService
import com.example.crm.lib.IdGenerator
import com.example.crm.lib.MapperService
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val mapperService: MapperService,
    private val userService: UserService,
    private val generator: IdGenerator,) {

    fun getAllOrders(pageable: Pageable): Page<OrderDTO> {
        val orders = orderRepository.findAll(pageable)
        return orders.map { mapperService.convertEntityToDto(it, OrderDTO::class.java) }
    }
    fun getUserOrders( user: CustomUserDetails): List<OrderDTO> {
        val orders = orderRepository.findAllByUserId(user.getId())
        return orders.map { mapperService.convertEntityToDto(it, OrderDTO::class.java) }
    }
    fun getUserOrdersByStatus(user: CustomUserDetails, status: OrderStatus): List<OrderDTO> {
        val orders = orderRepository.findAllByUserIdAndStatus(user.getId(), status)
        return orders.map { mapperService.convertEntityToDto(it, OrderDTO::class.java) }
    }

    fun saveOrder(orderDTO: OrderDTO, userDetails: CustomUserDetails): OrderDTO {

        val order = Order(
            totalPrice = orderDTO.totalPrice,
            status = orderDTO.status,

        ).apply {
            orderNumber = generator.generateOrderId("ORD")
            createdAt = LocalDateTime.now()
            user = userService.findUserById(userDetails.getId())
        }

        val savedOrder = orderRepository.save(order)

        return mapperService.convertEntityToDto (savedOrder, OrderDTO::class.java)
    }

    fun deleteOrder(id: UUID){
        if (!orderRepository.existsById(id)) {
            throw NoSuchElementException("Order with id $id not found")
        }
        orderRepository.deleteById(id)
    }

    fun getOrderById(id: UUID): OrderDTO {
        val order = orderRepository.findById(id)
            .orElseThrow {
                NoSuchElementException("Order with id $id not found")
            }
        return mapperService.convertEntityToDto(order, OrderDTO::class.java)
    }

    fun updateOrder(id: UUID, orderDTO: OrderDTO): OrderDTO {
        val existingOrder = orderRepository.findById(id)
            .orElseThrow { NoSuchElementException("Order with id $id not found") }

        val updatedOrder = Order(
            totalPrice = orderDTO.totalPrice,
            status = orderDTO.status,
        ).apply {
            updatedAt = LocalDateTime.now()
        }

        if (existingOrder != null) {
            updatedOrder.id = existingOrder.id
        }

        val savedOrder = orderRepository.save(updatedOrder)

        return mapperService.convertEntityToDto(savedOrder, OrderDTO::class.java)
    }

}
