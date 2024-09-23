package com.hamsoft.catalogservice.domain

class BookAlreadyExistException(isbn: String) : RuntimeException("A book with isbn $isbn already exists")