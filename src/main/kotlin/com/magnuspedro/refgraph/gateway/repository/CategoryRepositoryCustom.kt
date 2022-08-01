package com.magnuspedro.refgraph.gateway.repository

import com.magnuspedro.refgraph.entities.vertices.Article
import com.magnuspedro.refgraph.entities.vertices.Category
import reactor.core.publisher.Mono

interface CategoryRepositoryCustom {
    fun save(category: Category): Mono<Category>

    fun findCategoryByCode(code: String?): Mono<Category>
}