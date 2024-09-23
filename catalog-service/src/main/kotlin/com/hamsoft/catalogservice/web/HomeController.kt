package com.hamsoft.catalogservice.web

import com.hamsoft.catalogservice.config.BookShopProperties
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HomeController(private val bookShopProperties: BookShopProperties) {


    @GetMapping("/")
    fun welcome(): String{
        return bookShopProperties.greeting
    }
}