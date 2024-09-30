package com.hamsoft.orderservice.domain

import com.hamsoft.orderservice.client.BookClient
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class OrderService(private val orderRepository: OrderRepository, private  val bookClient: BookClient) {


    fun getAllOrders(): Flux<Order> {
        return orderRepository.findAll()
    }

    fun submitOrder(bookIsbn: String,quantity:Int): Mono<Order> {
       return bookClient.getBookByIsbn(bookIsbn)
            .map { Order(bookIsbn = bookIsbn, bookName = it.author + "-" + it.title, bookPrice = it.price, quantity = quantity, status = OrderStatus.ACCEPTED) }
            .defaultIfEmpty( Order(bookIsbn = bookIsbn, quantity = quantity, status = OrderStatus.REJECTED))
            .flatMap { orderRepository.save(it) }
    }



}