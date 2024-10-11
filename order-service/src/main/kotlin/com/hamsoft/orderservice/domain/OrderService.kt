package com.hamsoft.orderservice.domain

import com.hamsoft.orderservice.client.BookClient
import com.hamsoft.orderservice.event.OrderAcceptedMessage
import com.hamsoft.orderservice.event.OrderDispatchedMessage
import org.slf4j.LoggerFactory
import org.springframework.cloud.stream.function.StreamBridge
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val bookClient: BookClient,
    private val streamBridge: StreamBridge
) {

    private val log = LoggerFactory.getLogger(OrderService::class.java)


    fun getAllOrders(): Flux<Order> {
        return orderRepository.findAll()
    }

    @Transactional
    fun submitOrder(bookIsbn: String,quantity:Int): Mono<Order> {
       return bookClient.getBookByIsbn(bookIsbn)
            .map { Order(bookIsbn = bookIsbn, bookName = it.author + "-" + it.title, bookPrice = it.price, quantity = quantity, status = OrderStatus.ACCEPTED) }
            .defaultIfEmpty( Order(bookIsbn = bookIsbn, quantity = quantity, status = OrderStatus.REJECTED))
            .flatMap { orderRepository.save(it) }
            .doOnNext{
                publishOrderAcceptedEvent(it)
            }
    }

    fun consumeOrderDispatchedEvent(order: Flux<OrderDispatchedMessage>): Flux<Order> {
        return  order.flatMap { orderRepository.findById(it.orderId) }
            .map { buildDispatchedOrder(it) }
            .flatMap { orderRepository.save(it) }
    }

    private  fun buildDispatchedOrder(existingOrder: Order): Order {
        return  Order(
            id = existingOrder.id,
            version = existingOrder.version,
            bookIsbn = existingOrder.bookIsbn,
            bookName = existingOrder.bookName,
            bookPrice = existingOrder.bookPrice,
            quantity = existingOrder.quantity,
            status = OrderStatus.DISPATCHED,
            createdDate = existingOrder.createdDate,
            lastModifiedDate = existingOrder.lastModifiedDate,
        )
    }


    fun  publishOrderAcceptedEvent(order: Order){
        if(order.status != OrderStatus.ACCEPTED){
            return
        }
        val orderAcceptedMessage = OrderAcceptedMessage(orderId = order.id!!)
        log.info("publishing order accepted event with id ${order.id}")
        val result = streamBridge.send("acceptOrder-out-0",orderAcceptedMessage)
        log.info("The result of sending data for order with id ${order.id} =  $result")
    }


}