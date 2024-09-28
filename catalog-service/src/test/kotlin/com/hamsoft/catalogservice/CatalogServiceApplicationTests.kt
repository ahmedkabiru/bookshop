package com.hamsoft.catalogservice

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Import

@SpringBootTest
@Import(TestcontainersConfiguration::class)
class CatalogServiceApplicationTests {

	@Test
	fun contextLoads() {
	}

}
