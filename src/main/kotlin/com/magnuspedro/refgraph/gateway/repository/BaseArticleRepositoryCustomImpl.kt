package com.magnuspedro.refgraph.gateway.repository

import com.magnuspedro.refgraph.entities.requests.Citation
import com.magnuspedro.refgraph.entities.requests.CreateAndRelateArticle
import com.magnuspedro.refgraph.entities.requests.Publisher
import com.magnuspedro.refgraph.entities.requests.enums.ArticleType
import com.magnuspedro.refgraph.entities.requests.enums.ReferenceType
import com.magnuspedro.refgraph.entities.vertices.*
import com.magnuspedro.refgraph.entities.vertices.enums.PublisherType
import org.springframework.data.neo4j.core.ReactiveNeo4jTemplate
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.UUID

class BaseArticleRepositoryCustomImpl(private val neo4jTemplate: ReactiveNeo4jTemplate) : BaseArticleRepositoryCustom {


    override fun <T> createRelation(body: CreateAndRelateArticle): Mono<MutableList<T>> {
        requireNotNull(body.article)
        requireNotNull(body.citation)

        body.categoryId?.also {
            it.map { id ->
                findCategoryById(id).subscribe { category -> body.article.category = category }
            }
        }

        body.authorIds?.also {
            body.article.wrote = mutableListOf()
            it.map { id ->
                findAuthorById(id).subscribe { author -> body.article.wrote!!.add(author) }
            }
        }

        body.publishers?.also {
            findPublisherById(it).subscribe { publisher ->
                body.article.published = publisher as PublicationMedium?
            }
        }

        return Flux.fromIterable(body.citation.map { citation ->
            val type = citation.type.takeIf { it != null } ?: throw ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Type must not be null"
            )
            findById(citation).flatMap {
                    when (type) {
                        ReferenceType.CITED -> {
                            body.article.cited = mutableListOf(it!! as BaseArticle)
                        }
                        ReferenceType.REFERENCED -> {
                            body.article.referenced = mutableListOf(it!! as BaseArticle)
                        }
                    }
                    neo4jTemplate.save(body.article as T)
                }
        }).flatMap { mono -> mono }.collectList()
    }

    private fun findById(citation: Citation): Mono<out Any> {

        val articleTye = citation.articleType.takeIf { it != null } ?: throw ResponseStatusException(
            HttpStatus.BAD_REQUEST, "Article Type must not be null"
        )
        val uuid = citation.id.takeIf { it != null } ?: throw ResponseStatusException(
            HttpStatus.BAD_REQUEST, "Id must not be null"
        )

        val clazz = supportsArticleType(articleTye)
        return neo4jTemplate.findById(uuid, clazz)

    }

    private fun findCategoryById(id: UUID): Mono<Category> {
        return neo4jTemplate.findById(id, Category::class.java)
    }

    private fun findAuthorById(id: UUID): Mono<Author> {
        return neo4jTemplate.findById(id, Author::class.java)
    }

    private fun findPublisherById(publisher: Publisher): Mono<out Any> {

        val id = publisher.id.takeIf { it != null } ?: throw ResponseStatusException(
            HttpStatus.BAD_REQUEST, "Publisher id must not be null"
        )
        val type = publisher.type.takeIf { it != null } ?: throw ResponseStatusException(
            HttpStatus.BAD_REQUEST, "Publisher type must not be null"
        )

        val clazz = supportsPublisherType(type)
        return neo4jTemplate.findById(id, clazz)
    }

    private fun supportsPublisherType(publisherType: PublisherType): Class<*> = when (publisherType) {
        PublisherType.JOURNAL -> Journal::class.java
        PublisherType.CONFERENCE -> Conference::class.java
    }

    private fun supportsArticleType(articleType: ArticleType): Class<*> = when (articleType) {
        ArticleType.ARTICLE -> Article::class.java
        ArticleType.BOOK -> Book::class.java
        ArticleType.THESIS -> Thesis::class.java
        ArticleType.INPROCEEDINGS -> InProceedings::class.java
    }
}