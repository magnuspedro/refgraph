package com.magnuspedro.refgraph.gateway.repository

import com.magnuspedro.refgraph.entities.vertices.Category
import org.springframework.data.neo4j.core.ReactiveNeo4jTemplate
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono

class CategoryRepositoryCustomImpl(private val neo4jTemplate: ReactiveNeo4jTemplate) : CategoryRepositoryCustom {
    override fun save(category: Category): Mono<Category> {
        return findByCode(category.code).flatMap<Category?> {
            Mono.error(ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Category already exists"))
        }.switchIfEmpty(neo4jTemplate.save(category))
    }

    override fun findByCode(code: String?): Mono<Category> {
        return neo4jTemplate.findOne(
            "MATCH (Category:Category {code: \$code}) RETURN Category", mapOf(Pair("code", code)), Category::class.java
        )
    }
}