package com.magnuspedro.refgraph.gateway.repository

import com.magnuspedro.refgraph.entities.vertices.Keyword
import org.springframework.data.neo4j.core.ReactiveNeo4jTemplate
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono

class KeyWordRepositoryCustomImpl(private val neo4jTemplate: ReactiveNeo4jTemplate) : KeyWordRepositoryCustom {
    override fun save(keyword: Keyword): Mono<Keyword> {
        return findByName(keyword.name).flatMap<Keyword?> {
            Mono.error(ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Keyword already exists"))
        }.switchIfEmpty(neo4jTemplate.save(keyword))
    }

    override fun findByName(code: String?): Mono<Keyword> {
        return neo4jTemplate.findOne(
            "MATCH (Keyword:Keyword {code: \$code}) RETURN Keyword", mapOf(Pair("code", code)), Keyword::class.java
        )
    }
}