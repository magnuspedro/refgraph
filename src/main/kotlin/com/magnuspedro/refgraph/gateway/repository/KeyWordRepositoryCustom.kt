package com.magnuspedro.refgraph.gateway.repository

import com.magnuspedro.refgraph.entities.vertices.Keyword
import reactor.core.publisher.Mono

interface KeyWordRepositoryCustom {
    fun save(category: Keyword): Mono<Keyword>

    fun findByName(code: String?): Mono<Keyword>
}