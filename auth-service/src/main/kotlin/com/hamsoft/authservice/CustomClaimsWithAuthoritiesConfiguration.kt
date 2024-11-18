package com.hamsoft.authservice

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer
import java.util.stream.Collectors


@Configuration
class CustomClaimsWithAuthoritiesConfiguration {

    @Bean
    fun jwtTokenCustomizer(): OAuth2TokenCustomizer<JwtEncodingContext> {
        return OAuth2TokenCustomizer { context ->
            if (OAuth2TokenType.ACCESS_TOKEN == context.tokenType) {
                    val roles = AuthorityUtils.authorityListToSet(context.getPrincipal<Authentication>().authorities)
                            .stream()
                            .map { c: String -> c.replaceFirst("^ROLE_".toRegex(), "") }
                            .collect(Collectors.toSet())
                    context.claims.claim("roles", roles)
            }

            if(OidcParameterNames.ID_TOKEN == context.tokenType.value){
                val roles = AuthorityUtils.authorityListToSet(context.getPrincipal<Authentication>().authorities)
                    .stream()
                    .map { c: String -> c.replaceFirst("^ROLE_".toRegex(), "") }
                    .collect(Collectors.toSet())
                context.claims.claim("roles", roles)
            }

        }
    }
}