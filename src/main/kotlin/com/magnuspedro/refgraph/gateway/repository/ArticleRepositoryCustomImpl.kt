package com.magnuspedro.refgraph.gateway.repository

import com.magnuspedro.refgraph.entities.edges.CitationMedium
import com.magnuspedro.refgraph.entities.requests.*
import com.magnuspedro.refgraph.entities.vertices.Article
import com.magnuspedro.refgraph.extensions.Extensions.Companion.verifyNull
import org.springframework.data.neo4j.core.ReactiveNeo4jTemplate
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class ArticleRepositoryCustomImpl(
    private val neo4jTemplate: ReactiveNeo4jTemplate,
    private val categoryRepository: CategoryRepository,
    private val authorRepository: AuthorRepository,
    private val keyWordRepository: KeyWordRepository,
    private val publicationMediumRepository: PublicationMediumRepository,
    private val articleRepository: ArticleRepository
) : ArticleRepositoryCustom {

    override fun save(article: Article): Mono<Article> {
        return findByCode(article.code)!!.flatMap<Article?> {
            Mono.error(ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Article already exists"))
        }.switchIfEmpty(neo4jTemplate.save(article))
    }

    override fun findAll(): Flux<Article> {
        return articleRepository.findAll()
    }

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

    override fun relateAuthor(authorRelation: AuthorRelation): Mono<Article> {
        val author = verifyNull(authorRepository.findByCode(authorRelation.authorCode), "Author not found")
        val article = verifyNull(findByCode(authorRelation.articleCode)!!, "Article doesn't exists")

        return author.flatMap { aut ->
            article.flatMap { art ->
                art.wrote?.add(aut)
                neo4jTemplate.save(art)
            }
        }
    }

    override fun relateCategory(categoryRelation: CategoryRelation): Mono<Article> {
        val article = verifyNull(findByCode(categoryRelation.articleCode)!!, "Article doesn't exists")
        val category = verifyNull(
            categoryRepository.findByCode(categoryRelation.categoryCode), "Category doesn't exits"
        )

        return category.flatMap { cat ->
            article.flatMap { art ->
                art.category?.add(cat)
                neo4jTemplate.save(art)
            }
        }
    }

    override fun relateKeyword(keywordRelation: KeywordRelation): Mono<Article> {
        val keyword = verifyNull(keyWordRepository.findByName(keywordRelation.keywordName), "Keyword doesn't exists")
        val article = verifyNull(findByCode(keywordRelation.articleCode)!!, "Article doesn't exists")

        return keyword.flatMap { key ->
            article.flatMap { art ->
                art.keyword?.add(key)
                neo4jTemplate.save(art)
            }
        }
    }

    override fun relatePublicationMedium(publicationMediumRelation: PublicationMediumRelation): Mono<Article> {
        val publicationMedium = verifyNull(
            publicationMediumRepository.findByCode(publicationMediumRelation.publicationMediumCode),
            "Publication Medium doesn't exists"
        )
        val article = verifyNull(findByCode(publicationMediumRelation.articleCode)!!, "Article doesn't exists")

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
        val article1 = verifyNull(findByCode(articleRelation.firstArticleCode)!!, "Article doesn't exists")
        val article2 = verifyNull(findByCode(articleRelation.secondArticleCode)!!, "Article doesn't exists")

        return Pair(article1, article2)
    }

    override fun findByCode(code: String?): Mono<Article>? {
        val id = verifyNull(
            neo4jTemplate.findOne(
                "MATCH (Article:Article) WHERE (Article.code=\$code) RETURN Article",
                mapOf(Pair("code", code)),
                Article::class.java
            ), "Article doesn't exists"
        ).map { it.id }
        return articleRepository.findById(id)
    }
}