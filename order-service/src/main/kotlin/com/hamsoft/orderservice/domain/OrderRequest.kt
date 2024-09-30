package com.hamsoft.orderservice.domain

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class OrderRequest(

    @field:NotBlank(message = "Order ID cannot be blank")
    val isbn: String,

    @field:NotNull(message = "Book quantity must be defined")
    @field:Min(value = 1,message = "quantity must be at least 1")
    @field:Max(value = 5,message = "quantity can not be more than 5")
    val quantity: Int
)