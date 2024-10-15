package com.hamsoft.edgeservice.config

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactor.core.publisher.Mono
import java.security.Principal

@Configuration
class RateLimitConfig {
    @Bean
    fun keyResolver(): KeyResolver{
        return KeyResolver{
            it.getPrincipal<Principal>()
                .flatMap { p -> Mono.just(p.name) }
                .defaultIfEmpty("ANONYMOUS")
        }
    }
}