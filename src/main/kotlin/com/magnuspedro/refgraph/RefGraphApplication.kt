package com.magnuspedro.refgraph

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories
import org.springframework.data.neo4j.repository.config.EnableReactiveNeo4jRepositories

@SpringBootApplication
class RefGraphApplication

fun main(args: Array<String>) {
    runApplication<RefGraphApplication>(*args)
}