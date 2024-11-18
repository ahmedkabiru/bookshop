package com.hamsoft.catalogservice.domain

import org.springframework.stereotype.Service

@Service
class BookService(private val bookRepository: BookRepository) {

    fun viewBookList(): Iterable<Book> {
       return bookRepository.findAll()
    }

    fun  viewBookDetails(isbn: String): Book {
        return  bookRepository.findByIsbn(isbn) ?: throw BookNotFoundException(isbn)
    }

    fun addBook(book: Book): Book {
        if(bookRepository.existsByIsbn(book.isbn)) {
            throw  BookAlreadyExistException(book.isbn)
        }
        return bookRepository.save(book)
    }

    fun deleteBook(isbn: String) {
        bookRepository.deleteByIsbn(isbn)
    }

    fun editBook(isbn: String, book: Book): Book {
       return bookRepository.findByIsbn(isbn)
            ?.let {
                  val bookToUpdate = Book(
                      isbn = book.isbn,
                      title = book.title,
                      author = book.author,
                      price = book.price,
                      publisher = book.publisher
                  )
                  return bookRepository.save(bookToUpdate)
            } ?: addBook(book)
    }



}