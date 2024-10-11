package com.hamsoft.dispatcherservice

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.stream.binder.test.InputDestination
import org.springframework.cloud.stream.binder.test.OutputDestination
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration
import org.springframework.context.annotation.Import
import org.springframework.integration.support.MessageBuilder
import kotlin.test.assertEquals

@SpringBootTest
@Import(TestChannelBinderConfiguration::class)
class FunctionStreamIntegrationTest {

    @Autowired
    lateinit var inputDestination: InputDestination

    @Autowired
    lateinit var outputDestination: OutputDestination

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    fun whenOrderIsAcceptedThenDispatched(){
        val orderId = 121L
        val inputMessage = MessageBuilder.withPayload(OrderAcceptedMessage(orderId)).build()
        val expectedOutputMessage= MessageBuilder.withPayload(OrderDispatchedMessage(orderId)).build()
        inputDestination.send(inputMessage)
        assertEquals(expectedOutputMessage.payload, objectMapper.readValue(outputDestination.receive().payload,OrderDispatchedMessage::class.java))
    }

}