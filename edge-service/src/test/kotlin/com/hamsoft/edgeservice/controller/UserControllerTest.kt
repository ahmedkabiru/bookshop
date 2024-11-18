package com.hamsoft.edgeservice.controller

import com.hamsoft.edgeservice.config.SecurityConfig
import com.hamsoft.edgeservice.model.User
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository
import org.springframework.security.oauth2.core.oidc.StandardClaimNames
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers
import org.springframework.test.web.reactive.server.WebTestClient

@WebFluxTest(controllers = [UserController::class])
@Import(SecurityConfig::class)
class UserControllerTest{

    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockBean
    lateinit var clientRegistrationRepository: ReactiveClientRegistrationRepository

    @Test
    fun `when not authenticated then 401`(){
        webTestClient.get()
            .uri("/user")
            .exchange()
            .expectStatus()
            .isUnauthorized
    }

    @Test
    fun  `when authenticated then return user`(){
        val expectedUser = User(username = "Ahmed", firstName = "user", lastName = "", roles = listOf())

        webTestClient
            .mutateWith(configureMockOidcLogin(expectedUser))
            .get().uri("/user")
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody(User::class.java)
            .value{ user -> assertThat(user).isEqualTo(expectedUser)}
    }

    private fun  configureMockOidcLogin(expectedUser : User): SecurityMockServerConfigurers.OidcLoginMutator{
        return  SecurityMockServerConfigurers.mockOidcLogin().idToken {
            builder ->
            run {
                builder.claim(StandardClaimNames.PREFERRED_USERNAME, expectedUser.username)
                builder.claim(StandardClaimNames.GIVEN_NAME, expectedUser.firstName)
                builder.claim(StandardClaimNames.FAMILY_NAME, expectedUser.lastName)
            }
        }

    }

}