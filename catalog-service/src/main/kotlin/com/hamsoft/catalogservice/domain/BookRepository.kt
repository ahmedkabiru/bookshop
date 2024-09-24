package com.hamsoft.catalogservice.domain

import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.transaction.annotation.Transactional

interface BookRepository : CrudRepository<Book, Long> {

    fun findByIsbn(isbn: String): Book?

    fun existsByIsbn(isbn: String): Boolean

    fun save(book: Book): Book

    @Modifying
    @Transactional
    @Query("delete from books where isbn = :isbn")
    fun deleteByIsbn(isbn: String)
}