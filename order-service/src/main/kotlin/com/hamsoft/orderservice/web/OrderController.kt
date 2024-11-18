package com.hamsoft.orderservice.web

import com.hamsoft.orderservice.domain.Order
import com.hamsoft.orderservice.domain.OrderRequest
import com.hamsoft.orderservice.domain.OrderService
import jakarta.validation.Valid
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/orders")
class OrderController(private val orderService: OrderService) {

    val log:Logger = LoggerFactory.getLogger(OrderController::class.java)

    @GetMapping
    fun getAllOrders(): Flux<Order> {
        log.info("Get all orders")
        return  orderService.getAllOrders()
    }

    @PostMapping
    fun submitOrder(@RequestBody @Valid order: OrderRequest): Mono<Order> {
        return  orderService.submitOrder(order.isbn, order.quantity)
    }
}