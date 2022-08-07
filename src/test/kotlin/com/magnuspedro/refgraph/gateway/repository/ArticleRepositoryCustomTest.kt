package com.magnuspedro.refgraph.gateway.repository

import com.magnuspedro.refgraph.entities.requests.enums.ArticleType
import com.magnuspedro.refgraph.entities.vertices.Article
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.atLeastOnce
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

internal class ArticleRepositoryCustomTest {
    private val categoryRepository: CategoryRepository = mock()
    private val authorRepository: AuthorRepository = mock()
    private val keyWordRepository: KeyWordRepository = mock()
    private val publicationMediumRepository: PublicationMediumRepository = mock()
    private val articleRepository: ArticleRepository = mock()

    private lateinit var articleRepositoryCustom: ArticleRepositoryCustom

    @BeforeEach
    fun setup() {
        articleRepositoryCustom = ArticleRepositoryCustom(
            categoryRepository,
            authorRepository,
            keyWordRepository,
            publicationMediumRepository,
            articleRepository
        )
    }

    @Test
    fun `Should save a publication medium`() {
        val article = Article(id = "N", name = "name", articleType = ArticleType.ARTICLE)
        whenever(this.articleRepository.findById(article.getGeneratedId())).thenReturn(Mono.empty())
        whenever(this.articleRepository.save(article)).thenReturn(Mono.just(article))

        val result = articleRepositoryCustom.save(article)

        verify(this.articleRepository, atLeastOnce()).findById(article.getGeneratedId())
        verify(this.articleRepository, atLeastOnce()).save(article)
        StepVerifier
            .create(result)
            .expectNextMatches {
                it.id == article.id
                        && it.name == article.name
                        && it.articleType == article.articleType
            }
            .verifyComplete()
    }

    @Test
    fun `Should throws that publication medium already exists`() {
        val article = Article(id = "N", name = "name", articleType = ArticleType.ARTICLE)
        whenever(this.articleRepository.findById(article.getGeneratedId())).thenReturn(Mono.just(article))
        whenever(this.articleRepository.save(article)).thenReturn(Mono.just(article))

        val result = articleRepositoryCustom.save(article)

        verify(this.articleRepository, atLeastOnce()).findById(article.getGeneratedId())
        StepVerifier
            .create(result)
            .expectError(ResponseStatusException::class.java)
            .verify()
    }

    @Test
    fun `Should return all publication medium`() {
        val article = Article(id = "N", name = "name", articleType = ArticleType.ARTICLE)
        whenever(this.articleRepository.findAll()).thenReturn(Flux.just(article))

        val result = articleRepositoryCustom.findAll()

        verify(this.articleRepository, atLeastOnce()).findAll()
        StepVerifier
            .create(result)
            .expectNextMatches {
                it.id == article.id
                        && it.name == article.name
                        && it.articleType == article.articleType
            }
            .verifyComplete()
    }
}