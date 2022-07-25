package com.magnuspedro.refgraph

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication()
class RefGraphApplication

fun main(args: Array<String>) {
    runApplication<RefGraphApplication>(*args)
}