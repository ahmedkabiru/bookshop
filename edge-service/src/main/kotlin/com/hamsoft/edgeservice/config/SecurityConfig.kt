package com.hamsoft.edgeservice.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.oauth2.client.oidc.web.server.logout.OidcClientInitiatedServerLogoutSuccessHandler
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository
import org.springframework.security.oauth2.client.web.server.WebSessionServerOAuth2AuthorizedClientRepository
import org.springframework.security.web.csrf.CsrfToken
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.HttpStatusServerEntryPoint
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono


@EnableWebFluxSecurity
@Configuration
class SecurityConfig{


    @Autowired
    private lateinit var clientRegistrationRepository: ReactiveClientRegistrationRepository


    @Bean
    fun authorizedClientRepository(): ServerOAuth2AuthorizedClientRepository{
        return WebSessionServerOAuth2AuthorizedClientRepository()
    }

    @Bean
    fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return  http.authorizeExchange {
                exchange -> exchange
                    .pathMatchers("/books/**").permitAll()
                    .anyExchange().authenticated()
             }
            //.exceptionHandling{ exceptionHandling -> exceptionHandling.authenticationEntryPoint(HttpStatusServerEntryPoint(HttpStatus.UNAUTHORIZED))}
            .oauth2Login(Customizer.withDefaults())
            .csrf { csrf -> csrf.disable() }
           // .csrf { csrf -> csrf.csrfTokenRepository(CookieServerCsrfTokenRepository.withHttpOnlyFalse()) }
            .logout{logout -> logout.logoutSuccessHandler(oidcLogoutSuccessHandler())}
            .build()
    }

    private fun oidcLogoutSuccessHandler(): ServerLogoutSuccessHandler {
        val oidcLogoutSuccessHandler = OidcClientInitiatedServerLogoutSuccessHandler(clientRegistrationRepository)
        oidcLogoutSuccessHandler.setPostLogoutRedirectUri("{baseUrl}")
        return oidcLogoutSuccessHandler
    }

    @Bean
    fun csrfWebFilter(): WebFilter {
        return WebFilter { exchange: ServerWebExchange, chain: WebFilterChain ->
            exchange.response.beforeCommit {
                Mono.defer {
                    val csrfToken: Mono<CsrfToken>? =
                        exchange.getAttribute<Mono<CsrfToken>>(
                            CsrfToken::class.java.getName()
                        )
                    csrfToken?.then() ?: Mono.empty()
                }
            }
            chain.filter(exchange)
        }
    }


}