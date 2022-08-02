package com.magnuspedro.refgraph.gateway.rest

import com.magnuspedro.refgraph.entities.requests.*
import com.magnuspedro.refgraph.entities.vertices.Article
import com.magnuspedro.refgraph.gateway.repository.ArticleRepositoryCustom
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.apache.commons.text.WordUtils
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1/refgraph/article")
class ArticleController(
    private val articleRepository: ArticleRepositoryCustom,
) {
    @Operation(summary = "Create article", security = [SecurityRequirement(name = "bearerAuth")])
    @PostMapping
    fun createArticle(@RequestBody articleRequest: ArticleRequest): Mono<Article> {
        val article = Article(
            name = articleRequest.title,
            date = articleRequest.date,
            doi = articleRequest.doi,
            code = WordUtils.initials(articleRequest.title).uppercase(),
            pages = articleRequest.pages,
            publisher = articleRequest.publisher,
            edition = articleRequest.edition,
            bookTitle = articleRequest.bookTitle,
            school = articleRequest.school,
            articleType = articleRequest.articleType
        )
        return this.articleRepository.save(article)
    }

    @Operation(summary = "Cite article", security = [SecurityRequirement(name = "bearerAuth")])
    @PostMapping("/cited")
    fun citeArticle(@RequestBody articleRelation: ArticleRelation): Mono<Article> {
        return this.articleRepository.relateCited(articleRelation)
    }

    @Operation(summary = "Reference article", security = [SecurityRequirement(name = "bearerAuth")])
    @PostMapping("/referenced")
    fun referenceArticle(@RequestBody articleRelation: ArticleRelation): Mono<Article> {
        return this.articleRepository.relateReferenced(articleRelation)
    }

    @Operation(summary = "Relate category", security = [SecurityRequirement(name = "bearerAuth")])
    @PostMapping("/relate/category")
    fun relateCategory(@RequestBody categoryRelation: CategoryRelation): Mono<Article> {
        return this.articleRepository.relateCategory(categoryRelation)
    }

    @Operation(summary = "Relate author", security = [SecurityRequirement(name = "bearerAuth")])
    @PostMapping("/relate/author")
    fun relateAuthor(@RequestBody authorRelation: AuthorRelation): Mono<Article> {
        return this.articleRepository.relateAuthor(authorRelation)
    }

    @Operation(summary = "Relate keyword", security = [SecurityRequirement(name = "bearerAuth")])
    @PostMapping("/relate/keyword")
    fun relateKeyword(@RequestBody keywordRelation: KeywordRelation): Mono<Article> {
        return this.articleRepository.relateKeyword(keywordRelation)
    }

    @Operation(summary = "Relate publication medium", security = [SecurityRequirement(name = "bearerAuth")])
    @PostMapping("/relate/publication-medium")
    fun relateKeyword(@RequestBody publicationMedium: PublicationMediumRelation): Mono<Article> {
        return this.articleRepository.relatePublicationMedium(publicationMedium)
    }

    @Operation(summary = "Find all articles", security = [SecurityRequirement(name = "bearerAuth")])
    @GetMapping
    fun findAllArticles(): Flux<Article> {
        return this.articleRepository.findAll()
    }

    @Operation(summary = "Find articles by code", security = [SecurityRequirement(name = "bearerAuth")])
    @GetMapping("/{code}")
    fun findArticleByCode(@PathVariable code: String): Mono<Article>? {
        return this.articleRepository.findByCode(code)
    }
}