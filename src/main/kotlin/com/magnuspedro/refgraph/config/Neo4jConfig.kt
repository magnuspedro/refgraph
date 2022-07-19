package com.magnuspedro.refgraph.config

import org.neo4j.driver.Driver
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.neo4j.core.ReactiveDatabaseSelectionProvider
import org.springframework.data.neo4j.core.transaction.ReactiveNeo4jTransactionManager


@Configuration
class Neo4jConfig {

    @Bean
    fun reactiveTransactionManager(
        driver: Driver?,
        databaseNameProvider: ReactiveDatabaseSelectionProvider?
    ): ReactiveNeo4jTransactionManager? {
        return driver?.let { ReactiveNeo4jTransactionManager(it, databaseNameProvider!!) }
    }
}