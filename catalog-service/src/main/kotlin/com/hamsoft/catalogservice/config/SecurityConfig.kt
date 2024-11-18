package com.hamsoft.catalogservice.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter
import org.springframework.security.web.DefaultSecurityFilterChain

@EnableWebSecurity
@Configuration
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): DefaultSecurityFilterChain {
        return http.authorizeHttpRequests { authorize ->
            authorize
                .requestMatchers("/actuator/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/", "/books/**").permitAll()
                .anyRequest().hasRole("ADMIN")
        }.oauth2ResourceServer{ oauth2 -> oauth2.jwt {  }}
            .sessionManagement { sessionManagement ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }.csrf { csrf -> csrf.disable() }
            .build()
    }

    @Bean
    fun jwtAuthenticationConverter(): JwtAuthenticationConverter {
        val jwtGrantedAuthoritiesConverter = JwtGrantedAuthoritiesConverter()
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_")
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("roles")

        val jwtAuthenticationConverter = JwtAuthenticationConverter()
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter)

        return jwtAuthenticationConverter
    }
}