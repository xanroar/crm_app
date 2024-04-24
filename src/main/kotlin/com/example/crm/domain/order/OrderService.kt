package com.example.crm.domain.order

import com.example.crm.domain.order.model.Order
import com.example.crm.domain.order.model.OrderDTO
import com.example.crm.domain.order.model.OrderStatus
import com.example.crm.security.MyUserPrincipal
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import com.example.crm.domain.user.UserService
import com.example.crm.lib.MapperService
import org.springframework.stereotype.Service
import java.util.*

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val mapperService: MapperService,
    private val userService: UserService,
    private val orderFilter: OrderFilter,) {

    fun getAllOrders(pageable: Pageable): Page<OrderDTO> {
        val orders = orderRepository.findAll(pageable)
        return orders.map { mapperService.convertEntityToDto(it, OrderDTO::class.java) }
    }
    fun getUserOrders( user: MyUserPrincipal): List<OrderDTO> {
        val orders = orderRepository.findAllByUserId(user.getId())
        return orders.map { mapperService.convertEntityToDto(it, OrderDTO::class.java) }
    }
    fun getUserOrdersByStatus(user: MyUserPrincipal, status: OrderStatus): List<OrderDTO> {
        val orders = orderRepository.findAllByUserIdAndStatus(user.getId(), status)
        return orders.map { mapperService.convertEntityToDto(it, OrderDTO::class.java) }
    }

    fun saveOrder(orderDTO: OrderDTO, user: MyUserPrincipal): OrderDTO {
        val filteredOrderNumber = orderFilter.filterOrderNumber(orderDTO.orderNumber)

        val order = Order(
            orderNumber = filteredOrderNumber,
            totalPrice = orderDTO.totalPrice,
            status = orderDTO.status,
            user = userService.findUserById(user.getId())
        )

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

        val updatedOrder = mapperService.convertDtoToEntity(orderDTO, Order::class.java)

        if (existingOrder != null) {
            updatedOrder.id = existingOrder.id
        }

        val savedOrder = orderRepository.save(updatedOrder)

        return mapperService.convertEntityToDto(savedOrder, OrderDTO::class.java)
    }

}
