package com.hamsoft.catalogservice.domain

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.annotation.Version
import org.springframework.data.relational.core.mapping.Table
import java.time.Instant

@Table("books")
data class Book(

    @field:Id
    val id: Long? = null,

    @field:NotBlank(message = "Isbn cannot be blank")
    val isbn: String,

    @field:NotBlank(message = "Title cannot be blank")
    val title: String,

    @field:NotBlank(message = "Author cannot be blank")
    val author: String,

    @field:NotNull
    @field:Positive(message = "the book price can be greater than zero")
    val price: Double,

    val publisher:String,

    @field:CreatedDate
    val createdDate: Instant? = null,

    @field:LastModifiedDate
    val lastModifiedDate: Instant?= null,

    @field:CreatedBy
    val createdBy: String? = null,

    @field:LastModifiedBy
    val lastModifiedBy: String? =null,

    @Version
    val version: Int = 0
)
