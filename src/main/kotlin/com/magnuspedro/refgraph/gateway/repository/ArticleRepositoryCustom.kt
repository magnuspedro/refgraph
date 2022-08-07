package com.magnuspedro.refgraph.gateway.repository

import com.magnuspedro.refgraph.entities.edges.CitationMedium
import com.magnuspedro.refgraph.entities.requests.*
import com.magnuspedro.refgraph.entities.vertices.Article
import com.magnuspedro.refgraph.extensions.Extensions.Companion.verifyNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Repository
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
class ArticleRepositoryCustom(
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

    fun findById(id: String): Mono<Article> {
        return articleRepository.findById(id)
    }

    fun findAll(): Flux<Article> {
        return articleRepository.findAll()
    }

    fun relateCited(articleCitationRelation: ArticleCitationRelation): Mono<Article> {
        val (mainArticle, citedArticle) = findArticles(
            articleCitationRelation.mainArticleId,
            articleCitationRelation.citedArticleId
        )

        return mainArticle.flatMap { art1 ->
            citedArticle.flatMap { art2 ->
                art1.cited = art2
                articleRepository.save(art1)
            }
        }
    }

    fun relateReferenced(articleReferencingRelation: ArticleReferencingRelation): Mono<Article> {
        val (mainArticle, referencedArticle) = findArticles(
            articleReferencingRelation.mainArticleId,
            articleReferencingRelation.referencingArticleId
        )

        return mainArticle.flatMap { art1 ->
            referencedArticle.flatMap { art2 ->
                art1.referenced = art2
                articleRepository.save(art1)
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
                articleRepository.save(art)
            }
        }
    }

    fun relateKeyword(keywordRelation: KeywordRelation): Mono<Article> {
        val keyword = verifyNull(keyWordRepository.findById(keywordRelation.keywordName), "Keyword doesn't exists")
        val article = verifyNull(findById(keywordRelation.articleId), "Article doesn't exists")

        return keyword.flatMap { key ->
            article.flatMap { art ->
                art.keyword?.add(key)
                articleRepository.save(art)
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
                articleRepository.save(art)
            }
        }
    }

    private fun findArticles(firstId: String, secondId: String): Pair<Mono<Article>, Mono<Article>> {
        val article1 =
            verifyNull(findById(firstId), "Article doesn't exists")
        val article2 =
            verifyNull(findById(secondId), "Article doesn't exists")

        return Pair(article1, article2)
    }
}