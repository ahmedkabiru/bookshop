package com.hamsoft.orderservice.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class ClientConfig {

    @Bean
    fun webClient(clientProperties: ClientProperties): WebClient {
        return WebClient.builder().baseUrl(clientProperties.catalogServiceUri.toString()).build()
    }
}