package com.magnuspedro.refgraph.gateway.rest

import com.magnuspedro.refgraph.entities.requests.CreateAndRelateArticle
import com.magnuspedro.refgraph.entities.vertices.Article
import com.magnuspedro.refgraph.gateway.repository.ArticleRepository
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1/refgraph/article")
class ArticleController(
    val articleRepository: ArticleRepository
) {
    @Operation(summary = "Create article", security = [SecurityRequirement(name = "bearerAuth")])
    @PostMapping
    fun createArticle(@RequestBody article: Article): Mono<Article> {
        return this.articleRepository.save(article)
    }

    @Operation(summary = "Create article with relations", security = [SecurityRequirement(name = "bearerAuth")])
    @PostMapping("/citation")
    fun createArticleCitation(@RequestBody createAndRelateArticle: CreateAndRelateArticle): Mono<MutableList<Article>> {
        return this.articleRepository.createRelation(createAndRelateArticle)
    }

    @Operation(summary = "Find all articles", security = [SecurityRequirement(name = "bearerAuth")])
    @GetMapping
    fun findAllArticles(): Flux<Article> {
        return this.articleRepository.findAll()
    }
}