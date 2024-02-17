package com.example.crm.service

import com.example.crm.data.entity.Order
import com.example.crm.data.repositories.OrderRepository
import com.example.crm.dto.OrderDTO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*


@Service
class OrderService(private val orderRepository: OrderRepository,
                   private val mapperService: MapperService,) {

    fun getAllOrders(pageable: Pageable): Page<OrderDTO> {

        val orders = orderRepository.findAll(pageable)

        return orders.map { mapperService.convertEntityToDto(it, OrderDTO::class.java) }
    }

    fun saveOrder(orderDTO: OrderDTO): OrderDTO {
        val order = mapperService.convertDtoToEntity(orderDTO, Order::class.java )

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
