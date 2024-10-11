package com.hamsoft.dispatcherservice

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactor.core.publisher.Flux
import java.util.function.Function

@Configuration
class DispatchingFunctions {

    private val logger: Logger = LoggerFactory.getLogger(DispatchingFunctions::class.java)

    @Bean
    fun pack(): Function<OrderAcceptedMessage, Long> {
        return Function<OrderAcceptedMessage, Long> {
            logger.info("The order with id ${it.orderId} is packed.")
            it.orderId
        }
    }

    @Bean
    fun label(): Function<Flux<Long>, Flux<OrderDispatchedMessage>> = Function {
        it.map { orderId ->
            logger.info("The order with id $orderId is labeled.")
            OrderDispatchedMessage(orderId)
        }
    }

}