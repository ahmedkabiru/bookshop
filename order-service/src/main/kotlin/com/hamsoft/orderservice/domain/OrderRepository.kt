package com.hamsoft.orderservice.domain

import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : R2dbcRepository<Order, Long> {
}