package com.hamsoft.edgeservice.controller

import com.hamsoft.edgeservice.model.User
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class UserController {

    @GetMapping("/user")
    fun getUser(@AuthenticationPrincipal oidcUser: OidcUser): Mono<User> {
       return Mono.just(
           User(
               username = oidcUser.preferredUsername,
               firstName = oidcUser.name,
               lastName = oidcUser.familyName,
               roles = oidcUser.getClaimAsStringList("roles")
           )
       )
    }
}