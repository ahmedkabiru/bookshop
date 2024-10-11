package com.hamsoft.dispatcherservice

import org.junit.jupiter.api.Test

import java.util.function.Function
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.function.context.FunctionCatalog
import org.springframework.cloud.function.context.test.FunctionalSpringBootTest
import reactor.core.publisher.Flux
import reactor.test.StepVerifier

@FunctionalSpringBootTest
class DispatchingFunctionsIntegrationTest {

    @Autowired
    lateinit var catalog: FunctionCatalog

    @Test
    fun packAndLabelOrder(){
        val packAndLabel: Function<OrderAcceptedMessage, Flux<OrderDispatchedMessage>> =
            catalog.lookup(Function::class.java, "pack|label")
        val orderId = 121L
        StepVerifier.create(packAndLabel.apply(OrderAcceptedMessage(orderId)))
            .expectNextMatches { it.equals(OrderDispatchedMessage(orderId)) }
            .verifyComplete()
    }
}