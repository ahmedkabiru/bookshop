package com.hamsoft.catalogservice.config

import com.hamsoft.catalogservice.domain.Book
import com.hamsoft.catalogservice.domain.BookRepository
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.annotation.Profile
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
@Profile("testdata")
@ConditionalOnProperty(name = ["polar.testdata.enabled"], havingValue = "true")
class BookDataLoader(private val bookRepository: BookRepository) {

    @EventListener(ApplicationReadyEvent::class)
    fun loadBookTestData(){
        val book1 = Book("1111","A", "John", 20.00)
        val book2 = Book("2222","B", "Kim", 30.00)
        bookRepository.save(book1)
        bookRepository.save(book2)
    }
}