package com.hamsoft.catalogservice.domain

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

data class Book(

 @field:NotBlank(message = "Isbn cannot be blank")
 val isbn: String,

 @field:NotBlank(message = "Title cannot be blank")
 val title: String,

 @field:NotBlank(message = "Author cannot be blank")
 val author: String,

 @field:NotNull
 @field:Positive(message = "the book price can be greater than zero")
 val price: Double

)
