package com.magnuspedro.refgraph.gateway.repository

import com.magnuspedro.refgraph.entities.vertices.Author
import org.springframework.data.neo4j.core.ReactiveNeo4jTemplate
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono

class AuthorRepositoryCustomImpl(private val neo4jTemplate: ReactiveNeo4jTemplate) : AuthorRepositoryCustom {
    override fun save(author: Author): Mono<Author> {
        return findAuthorByCode(author.code).flatMap<Author?> {
            Mono.error(ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Author already exists"))
        }.switchIfEmpty(neo4jTemplate.save(author))
    }


    override fun findAuthorByCode(code: String?): Mono<Author> {
        return neo4jTemplate.findOne(
            "MATCH (Author:Author {code: \$code}) RETURN Category", mapOf(Pair("code", code)), Author::class.java
        )
    }
}