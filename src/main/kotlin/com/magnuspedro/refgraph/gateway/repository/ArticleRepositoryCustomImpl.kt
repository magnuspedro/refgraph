package com.magnuspedro.refgraph.gateway.repository

import com.magnuspedro.refgraph.entities.requests.ArticleRelation
import com.magnuspedro.refgraph.entities.requests.CategoryRelation
import com.magnuspedro.refgraph.entities.vertices.Article
import com.magnuspedro.refgraph.entities.vertices.Category
import org.springframework.data.neo4j.core.ReactiveNeo4jTemplate
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono
import reactor.core.publisher.SynchronousSink

class ArticleRepositoryCustomImpl(
    private val neo4jTemplate: ReactiveNeo4jTemplate,
    private val categoryRepository: CategoryRepository
) : ArticleRepositoryCustom {
    override fun relateCited(articleRelation: ArticleRelation): Mono<Article> {
        val (article1, article2) = findArticles(articleRelation)

        return article1.flatMap { art1 ->
            article2.flatMap { art2 ->
                art1.cited = art2
                neo4jTemplate.save(art1)
            }
        }
    }

    override fun relateReferenced(articleRelation: ArticleRelation): Mono<Article> {
        val (article1, article2) = findArticles(articleRelation)

        return article2.flatMap { art2 ->
            article1.flatMap { art1 ->
                art2.cited = art1
                neo4jTemplate.save(art2)
            }
        }
    }

    override fun save(article: Article): Mono<Article> {
        return findArticleByCode(article.code).flatMap<Article?> {
            Mono.error(ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Article already exists"))
        }.switchIfEmpty(neo4jTemplate.save(article))
    }

    override fun relateCategory(categoryRelation: CategoryRelation): Mono<Article> {
        val article = verifyNull(findArticleByCode(categoryRelation.articleCode), "Article doesn't exists")
        val category =
            verifyNull(categoryRepository.findCategoryByCode(categoryRelation.categoryCode), "Category doesn't exits")

        return category.flatMap { cat ->
            article.flatMap { art ->
                art.category = cat
                neo4jTemplate.save(art)
            }
        }
    }

    private fun findArticles(articleRelation: ArticleRelation): Pair<Mono<Article>, Mono<Article>> {
        val article1 = findArticleByCode(articleRelation.firstArticleCode)
        val article2 = findArticleByCode(articleRelation.secondArticleCode)

        return Pair(article1, article2)
    }

    private fun findArticleByCode(code: String?): Mono<Article> {
        return neo4jTemplate.findOne(
            "MATCH (Article:Article {code: \$code}) RETURN Article", mapOf(Pair("code", code)), Article::class.java
        )
    }

    private fun <T> verifyNull(mono: Mono<T>, message: String): Mono<T> {
        return mono.switchIfEmpty(
            Mono.error(
                ResponseStatusException(
                    HttpStatus.UNPROCESSABLE_ENTITY, message
                )
            )
        )
    }
}