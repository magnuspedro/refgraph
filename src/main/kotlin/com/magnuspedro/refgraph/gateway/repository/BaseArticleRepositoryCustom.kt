package com.magnuspedro.refgraph.gateway.repository

import com.magnuspedro.refgraph.entities.requests.CreateAndRelateArticle
import reactor.core.publisher.Mono

interface BaseArticleRepositoryCustom {
    fun <T> createRelation(body: CreateAndRelateArticle): Mono<MutableList<T>>

}