package com.magnuspedro.refgraph.gateway.repository

import com.magnuspedro.refgraph.entities.requests.*
import com.magnuspedro.refgraph.entities.vertices.Article
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface ArticleRepositoryCustom {
    fun relateCited(articleRelation: ArticleRelation): Mono<Article>
    fun relateReferenced(articleRelation: ArticleRelation): Mono<Article>
    fun save(article: Article): Mono<Article>
    fun relateCategory(categoryRelation: CategoryRelation): Mono<Article>
    fun relateAuthor(authorRelation: AuthorRelation): Mono<Article>
    fun relateKeyword(keywordRelation: KeywordRelation): Mono<Article>
    fun relatePublicationMedium(publicationMediumRelation: PublicationMediumRelation): Mono<Article>
    fun findByCode(code: String?): Mono<Article>?
    fun findAll(): Flux<Article>
}