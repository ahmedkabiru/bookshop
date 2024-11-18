package com.hamsoft.catalogservice.web

import com.hamsoft.catalogservice.domain.Book
import com.hamsoft.catalogservice.domain.BookService
import jakarta.validation.Valid
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/books")
class BookController (private val bookService: BookService) {

    val log: Logger = LoggerFactory.getLogger(BookController::class.java)


    @GetMapping
    fun get(): Iterable<Book> {
        log.info("Fetching the list of books in the catalog")
        return bookService.viewBookList()
    }

    @GetMapping("{isbn}")
    fun getByIsbn(@PathVariable isbn: String): Book {
        return bookService.viewBookDetails(isbn)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun post(@Valid @RequestBody book: Book): Book {
        return bookService.addBook(book)
    }

    @DeleteMapping("{isbn}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable isbn: String){
        bookService.deleteBook(isbn)
    }

    @PutMapping("{isbn}")
    fun put(@PathVariable isbn: String, @Valid @RequestBody book: Book): Book {
        return bookService.editBook(isbn, book)
    }

}