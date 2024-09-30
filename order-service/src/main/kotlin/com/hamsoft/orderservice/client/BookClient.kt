package com.hamsoft.orderservice.client

import com.hamsoft.orderservice.domain.Book
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException
import reactor.core.publisher.Mono
import reactor.util.retry.Retry
import java.time.Duration

const val BOOK_ROOT_API_PATH = "/books/"
@Component
class BookClient(private val webClient: WebClient) {

    fun getBookByIsbn(isbn: String): Mono<Book> {
        return webClient
            .get()
            .uri(BOOK_ROOT_API_PATH + isbn)
            .retrieve()
            .bodyToMono(Book::class.java)
            .timeout(Duration.ofSeconds(3),Mono.empty())
            .onErrorResume ( WebClientResponseException.NotFound::class.java) { Mono.empty() }
            .retryWhen(Retry.backoff(3, Duration.ofMillis(100)))
            .onErrorResume ( Exception::class.java){ Mono.empty() }
    }




}