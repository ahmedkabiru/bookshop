package com.hamsoft.orderservice.domain

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.annotation.Version
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal
import java.time.Instant

@Table("orders")
data class Order(

    @Id
    var id: Long? = null,

    val bookIsbn: String,

    val bookName: String?= null,

    val bookPrice: Double?= null,

    val quantity: Int,

    val status: OrderStatus,

    @field:CreatedDate
    val createdDate: Instant? = null,

    @field:LastModifiedDate
    val lastModifiedDate: Instant?= null,

    @Version
    val version: Int = 0,

    )


enum class OrderStatus {
    ACCEPTED,
    REJECTED,
    DISPATCHED
}