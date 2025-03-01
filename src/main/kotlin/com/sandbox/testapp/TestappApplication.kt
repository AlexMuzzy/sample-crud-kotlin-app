package com.sandbox.testapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TestappApplication

fun main(args: Array<String>) {
    runApplication<TestappApplication>(*args)
}
