package com.hamsoft.catalogservice.config

import com.hamsoft.catalogservice.domain.Book
import com.hamsoft.catalogservice.domain.BookRepository
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.annotation.Profile
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
//@Profile("testdata")
@ConditionalOnProperty(name = ["bookshop.testdata.enabled"], havingValue = "true")
class BookDataLoader(private val bookRepository: BookRepository) {

    @EventListener(ApplicationReadyEvent::class)
    fun loadBookTestData(){
        bookRepository.deleteAll()
        val book1 = Book(isbn ="1111", title= "AA",  author =  "John", price = 20.00, publisher = "Packt")
        val book2 = Book(isbn="2222", title = "BB", author =  "Kim", price = 30.00, publisher = "Packt")
        bookRepository.saveAll(listOf(book1,book2))
    }
}