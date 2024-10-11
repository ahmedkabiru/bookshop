package com.hamsoft.orderservice.event

import com.hamsoft.orderservice.domain.OrderService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactor.core.publisher.Flux
import java.util.function.Consumer

@Configuration
class OrderFunctions {

    private val log:Logger = LoggerFactory.getLogger(OrderFunctions::class.java)

    @Bean
    fun dispatchOrder(orderService: OrderService): Consumer<Flux<OrderDispatchedMessage>>{
         return Consumer {
            orderService.consumeOrderDispatchedEvent(it)
                .doOnNext { orderId -> log.info("The order with $orderId hsa been dispatched!") }
                .subscribe()
         }
    }
}