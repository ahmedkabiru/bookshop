package com.hamsoft.catalogservice.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "bookshop")
class BookShopProperties {

    lateinit var greeting:String
}