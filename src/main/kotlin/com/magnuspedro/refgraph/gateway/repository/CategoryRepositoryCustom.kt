package com.magnuspedro.refgraph.gateway.repository

import com.magnuspedro.refgraph.entities.vertices.Category
import reactor.core.publisher.Mono

interface CategoryRepositoryCustom {
    fun save(category: Category): Mono<Category>

    fun findByCode(code: String?): Mono<Category>
}