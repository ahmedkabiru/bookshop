package com.hamsoft.catalogservice.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import java.util.Optional

@Configuration
@EnableJdbcAuditing
class DataConfig {


    @Bean
    fun auditorAware(): AuditorAware<String> {
        return AuditorAware<String> {
            Optional.ofNullable(SecurityContextHolder.getContext())
                .map { it.authentication }
                .filter { it.isAuthenticated }
                .map { it.name }
        }
    }
}