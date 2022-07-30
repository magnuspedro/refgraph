package com.magnuspedro.refgraph.gateway.rest

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
import com.magnuspedro.refgraph.entities.requests.ArticleRequest as ArticleRequest

@RestController
@RequestMapping("/api/v1/refgraph/article")
class ArticleController(
    val articleRepository: ArticleRepository
) {
    @Operation(summary = "Create article", security = [SecurityRequirement(name = "bearerAuth")])
    @PostMapping
    fun createArticle(@RequestBody articleRequest: ArticleRequest): Mono<Article> {
        val article = Article(
            title = articleRequest.title,
            date = articleRequest.date,
            doi = articleRequest.doi,
            code = articleRequest.code,
            pages = articleRequest.code
        )
        return this.articleRepository.save(article)
    }

    @Operation(summary = "Find all articles", security = [SecurityRequirement(name = "bearerAuth")])
    @GetMapping
    fun findAllArticles(): Flux<Article> {
        return this.articleRepository.findAll()
    }
}