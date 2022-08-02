package com.magnuspedro.refgraph.gateway.repository

import com.magnuspedro.refgraph.entities.vertices.Author
import reactor.core.publisher.Mono

interface AuthorRepositoryCustom {
    fun save(author: Author): Mono<Author>
    fun findByCode(code: String?): Mono<Author>
}