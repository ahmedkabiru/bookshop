package com.hamsoft.catalogservice.domain

import com.hamsoft.catalogservice.domain.Book

interface BookRepository {

    fun findAll(): List<Book>

    fun findByIsbn(isbn: String): Book?

    fun existsByIsbn(isbn: String): Boolean

    fun save(book: Book): Book

    fun deleteByIsbn(isbn: String)
}