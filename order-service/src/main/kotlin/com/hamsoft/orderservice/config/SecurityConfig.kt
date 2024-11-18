package com.hamsoft.orderservice.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.savedrequest.NoOpServerRequestCache

@EnableWebFluxSecurity
@Configuration
class SecurityConfig {



    @Bean
    fun filterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return  http.authorizeExchange{
            exchange -> exchange
                .pathMatchers("/actuator/**").permitAll()
                .anyExchange().authenticated()
        }.oauth2ResourceServer {
                it.jwt {  }
        }.requestCache { requestCache ->requestCache.requestCache(NoOpServerRequestCache.getInstance())}
            .csrf { csrf -> csrf.disable() }
            .build()
    }
}