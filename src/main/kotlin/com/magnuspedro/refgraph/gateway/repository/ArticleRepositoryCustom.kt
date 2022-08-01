package com.magnuspedro.refgraph.gateway.repository

import com.magnuspedro.refgraph.entities.requests.ArticleRelation
import com.magnuspedro.refgraph.entities.requests.CategoryRelation
import com.magnuspedro.refgraph.entities.vertices.Article
import reactor.core.publisher.Mono

interface ArticleRepositoryCustom {
    fun relateCited(articleRelation: ArticleRelation): Mono<Article>
    fun relateReferenced(articleRelation: ArticleRelation): Mono<Article>
    fun save(article: Article): Mono<Article>
    fun relateCategory(categoryRelation: CategoryRelation): Mono<Article>
}