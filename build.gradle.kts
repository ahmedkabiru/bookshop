plugins {
    val kotlinVersion = "1.9.25"
    kotlin("jvm") version kotlinVersion apply false
    kotlin("plugin.spring") version kotlinVersion apply false
    id("org.springframework.boot") version "3.3.4" apply false
    id ("io.spring.dependency-management") version "1.1.6" apply false
}

allprojects{
    group = "com.hamsoft"
    version = "1.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}
