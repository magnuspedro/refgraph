package com.magnuspedro.refgraph.gateway.rest

import com.magnuspedro.refgraph.entities.requests.CreateAndRelateArticle
import com.magnuspedro.refgraph.entities.vertices.Article
import com.magnuspedro.refgraph.gateway.repository.ArticleRepository
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1/refgraph")
class ArticleController(
    val articleRepository: ArticleRepository
) {

    @PostMapping("/article")
    fun createArticle(@RequestBody article: Article): Mono<Article> {
        return this.articleRepository.save(article)
    }

    @PostMapping("/article/citation")
    fun createArticleCitation(@RequestBody createAndRelateArticle: CreateAndRelateArticle): Mono<MutableList<Article>> {
        return this.articleRepository.createRelation(createAndRelateArticle)
    }
}