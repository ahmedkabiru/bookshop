package com.hamsoft.orderservice.config

import jakarta.validation.constraints.NotNull
import org.springframework.boot.context.properties.ConfigurationProperties
import java.net.URI


@ConfigurationProperties(prefix = "bookshop")
class ClientProperties {
    @field:NotNull
    lateinit var catalogServiceUri: URI
}