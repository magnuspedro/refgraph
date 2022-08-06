package com.magnuspedro.refgraph.gateway.repository

import com.magnuspedro.refgraph.entities.edges.CitationMedium
import com.magnuspedro.refgraph.entities.requests.*
import com.magnuspedro.refgraph.entities.vertices.Article
import com.magnuspedro.refgraph.extensions.Extensions.Companion.verifyNull
import org.springframework.data.neo4j.core.ReactiveNeo4jTemplate
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Repository
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
class ArticleRepositoryCustom(
    private val neo4jTemplate: ReactiveNeo4jTemplate,
    private val categoryRepository: CategoryRepository,
    private val authorRepository: AuthorRepository,
    private val keyWordRepository: KeyWordRepository,
    private val publicationMediumRepository: PublicationMediumRepository,
    private val articleRepository: ArticleRepository
) {

    fun save(article: Article): Mono<Article> {
        return findById(article.getGeneratedId()).flatMap<Article?> {
            Mono.error(ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Article already exists"))
        }.switchIfEmpty(articleRepository.save(article))
    }

    fun relateCited(articleRelation: ArticleRelation): Mono<Article> {
        val (article1, article2) = findArticles(articleRelation)

        return article1.flatMap { art1 ->
            article2.flatMap { art2 ->
                art1.cited = art2
                neo4jTemplate.save(art1)
            }
        }
    }

    fun relateReferenced(articleRelation: ArticleRelation): Mono<Article> {
        val (article1, article2) = findArticles(articleRelation)

        return article2.flatMap { art2 ->
            article1.flatMap { art1 ->
                art2.cited = art1
                neo4jTemplate.save(art2)
            }
        }
    }

    fun relateAuthor(authorRelation: AuthorRelation): Mono<Article> {
        val article = verifyNull(findById(authorRelation.articleId), "Article doesn't exists")
        val author = verifyNull(authorRepository.findById(authorRelation.authorId), "Author not found")

        return author.flatMap { aut ->
            article.flatMap { art ->
                art.wrote?.add(aut)
                articleRepository.save(art)
            }
        }
    }

    fun relateCategory(categoryRelation: CategoryRelation): Mono<Article> {
        val article = verifyNull(findById(categoryRelation.articleId), "Article doesn't exists")
        val category = verifyNull(
            categoryRepository.findById(categoryRelation.categoryId), "Category doesn't exits"
        )

        return category.flatMap { cat ->
            article.flatMap { art ->
                art.category?.add(cat)
                neo4jTemplate.save(art)
            }
        }
    }

    fun relateKeyword(keywordRelation: KeywordRelation): Mono<Article> {
        val keyword = verifyNull(keyWordRepository.findById(keywordRelation.keywordName), "Keyword doesn't exists")
        val article = verifyNull(findById(keywordRelation.articleCode), "Article doesn't exists")

        return keyword.flatMap { key ->
            article.flatMap { art ->
                art.keyword?.add(key)
                neo4jTemplate.save(art)
            }
        }
    }

    fun relatePublicationMedium(publicationMediumRelation: PublicationMediumRelation): Mono<Article> {
        val publicationMedium = verifyNull(
            publicationMediumRepository.findById(publicationMediumRelation.publicationMediumId),
            "Publication Medium doesn't exists"
        )
        val article =
            verifyNull(findById(publicationMediumRelation.articleId), "Article doesn't exists")

        return publicationMedium.flatMap { pbm ->
            article.flatMap { art ->
                val citationMedium = CitationMedium(
                    medium = pbm,
                    issue = publicationMediumRelation.issue,
                    version = publicationMediumRelation.version,
                    volume = publicationMediumRelation.volume,
                    date = publicationMediumRelation.date
                )

                art.published = citationMedium
                neo4jTemplate.save(art)
            }
        }
    }

    private fun findArticles(articleRelation: ArticleRelation): Pair<Mono<Article>, Mono<Article>> {
        val article1 =
            verifyNull(findById(articleRelation.firstArticleCode), "Article doesn't exists")
        val article2 =
            verifyNull(findById(articleRelation.secondArticleCode), "Article doesn't exists")

        return Pair(article1, article2)
    }

    fun findById(id: String): Mono<Article> {
        return articleRepository.findById(id)
    }

    fun findAll(): Flux<Article> {
        return articleRepository.findAll()
    }
}