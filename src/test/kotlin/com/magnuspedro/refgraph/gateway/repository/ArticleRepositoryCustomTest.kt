package com.magnuspedro.refgraph.gateway.repository

import com.magnuspedro.refgraph.entities.requests.*
import com.magnuspedro.refgraph.entities.requests.enums.ArticleType
import com.magnuspedro.refgraph.entities.vertices.*
import com.magnuspedro.refgraph.entities.vertices.enums.PublisherType
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
            .expectNext(article)
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
            .expectNext(article)
            .verifyComplete()
    }

    @Test
    fun `Should return publication medium by id`() {
        val article = Article(id = "N", name = "name", articleType = ArticleType.ARTICLE)
        whenever(this.articleRepository.findById(article.getGeneratedId())).thenReturn(Mono.just(article))

        val result = articleRepositoryCustom.findById(article.getGeneratedId())

        verify(this.articleRepository, atLeastOnce()).findById(article.getGeneratedId())
        StepVerifier
            .create(result)
            .expectNext(article)
            .verifyComplete()
    }

    @Test
    fun `Should cite an article`() {
        val article = Article(id = "N", name = "name", articleType = ArticleType.ARTICLE)
        val citedArticle = Article(id = "A", name = "article", articleType = ArticleType.ARTICLE)
        val articleCitationRelation = ArticleCitationRelation(article.getGeneratedId(), citedArticle.getGeneratedId())
        whenever(this.articleRepository.findById(article.getGeneratedId())).thenReturn(Mono.just(article))
        whenever(this.articleRepository.findById(citedArticle.getGeneratedId())).thenReturn(Mono.just(citedArticle))
        whenever(this.articleRepository.save(article)).thenReturn(Mono.just(article))

        val result = articleRepositoryCustom.relateCited(articleCitationRelation)

        verify(this.articleRepository, atLeastOnce()).findById(article.getGeneratedId())
        verify(this.articleRepository, atLeastOnce()).findById(citedArticle.getGeneratedId())
        StepVerifier
            .create(result)
            .expectNext(article)
            .verifyComplete()
    }

    @Test
    fun `Should return error on first cited article null`() {
        val article = Article(id = "N", name = "name", articleType = ArticleType.ARTICLE)
        val citedArticle = Article(id = "A", name = "article", articleType = ArticleType.ARTICLE)
        val articleCitationRelation = ArticleCitationRelation(article.getGeneratedId(), citedArticle.getGeneratedId())
        whenever(this.articleRepository.findById(article.getGeneratedId())).thenReturn(Mono.empty())
        whenever(this.articleRepository.findById(citedArticle.getGeneratedId())).thenReturn(Mono.just(citedArticle))

        val result = articleRepositoryCustom.relateCited(articleCitationRelation)

        verify(this.articleRepository, atLeastOnce()).findById(article.getGeneratedId())
        verify(this.articleRepository, atLeastOnce()).findById(citedArticle.getGeneratedId())
        StepVerifier
            .create(result)
            .expectError(ResponseStatusException::class.java)
            .verify()
    }

    @Test
    fun `Should return error on second cited article null`() {
        val article = Article(id = "N", name = "name", articleType = ArticleType.ARTICLE)
        val citedArticle = Article(id = "A", name = "article", articleType = ArticleType.ARTICLE)
        val articleCitationRelation = ArticleCitationRelation(article.getGeneratedId(), citedArticle.getGeneratedId())
        whenever(this.articleRepository.findById(article.getGeneratedId())).thenReturn(Mono.just(article))
        whenever(this.articleRepository.findById(citedArticle.getGeneratedId())).thenReturn(Mono.empty())

        val result = articleRepositoryCustom.relateCited(articleCitationRelation)

        verify(this.articleRepository, atLeastOnce()).findById(article.getGeneratedId())
        verify(this.articleRepository, atLeastOnce()).findById(citedArticle.getGeneratedId())
        StepVerifier
            .create(result)
            .expectError(ResponseStatusException::class.java)
            .verify()
    }

    @Test
    fun `Should reference an article`() {
        val article = Article(id = "N", name = "name", articleType = ArticleType.ARTICLE)
        val referencedArticle = Article(id = "A", name = "article", articleType = ArticleType.ARTICLE)
        val articleReferencingRelation = ArticleReferencingRelation(
            article.getGeneratedId(), referencedArticle.getGeneratedId()
        )
        whenever(this.articleRepository.findById(article.getGeneratedId())).thenReturn(Mono.just(article))
        whenever(this.articleRepository.findById(referencedArticle.getGeneratedId())).thenReturn(
            Mono.just(
                referencedArticle
            )
        )
        whenever(this.articleRepository.save(article)).thenReturn(Mono.just(article))

        val result = articleRepositoryCustom.relateReferenced(articleReferencingRelation)

        verify(this.articleRepository, atLeastOnce()).findById(article.getGeneratedId())
        verify(this.articleRepository, atLeastOnce()).findById(referencedArticle.getGeneratedId())
        StepVerifier
            .create(result)
            .expectNext(article)
            .verifyComplete()
    }

    @Test
    fun `Should return error on first referenced article null`() {
        val article = Article(id = "N", name = "name", articleType = ArticleType.ARTICLE)
        val referencedArticle = Article(id = "A", name = "article", articleType = ArticleType.ARTICLE)
        val articleReferencingRelation = ArticleReferencingRelation(
            article.getGeneratedId(), referencedArticle.getGeneratedId()
        )
        whenever(this.articleRepository.findById(article.getGeneratedId())).thenReturn(Mono.empty())
        whenever(this.articleRepository.findById(referencedArticle.getGeneratedId())).thenReturn(
            Mono.just(
                referencedArticle
            )
        )
        val result = articleRepositoryCustom.relateReferenced(articleReferencingRelation)

        verify(this.articleRepository, atLeastOnce()).findById(article.getGeneratedId())
        verify(this.articleRepository, atLeastOnce()).findById(referencedArticle.getGeneratedId())
        StepVerifier
            .create(result)
            .expectError(ResponseStatusException::class.java)
            .verify()
    }

    @Test
    fun `Should return error on second referenced article null`() {
        val article = Article(id = "N", name = "name", articleType = ArticleType.ARTICLE)
        val referencedArticle = Article(id = "A", name = "article", articleType = ArticleType.ARTICLE)
        val articleReferencingRelation = ArticleReferencingRelation(
            article.getGeneratedId(), referencedArticle.getGeneratedId()
        )
        whenever(this.articleRepository.findById(article.getGeneratedId())).thenReturn(Mono.just(article))
        whenever(this.articleRepository.findById(referencedArticle.getGeneratedId())).thenReturn(Mono.empty())

        val result = articleRepositoryCustom.relateReferenced(articleReferencingRelation)

        verify(this.articleRepository, atLeastOnce()).findById(article.getGeneratedId())
        verify(this.articleRepository, atLeastOnce()).findById(referencedArticle.getGeneratedId())
        StepVerifier
            .create(result)
            .expectError(ResponseStatusException::class.java)
            .verify()
    }

    @Test
    fun `Should relate author`() {
        val article = Article(id = "N", name = "name", articleType = ArticleType.ARTICLE)
        val author = Author(id = "P", name = "Pedro")
        val authorRelation = AuthorRelation(articleId = article.getGeneratedId(), authorId = author.getGeneratedId())
        whenever(this.articleRepository.findById(article.getGeneratedId())).thenReturn(Mono.just(article))
        whenever(this.authorRepository.findById(author.getGeneratedId())).thenReturn(Mono.just(author))
        whenever(this.articleRepository.save(article)).thenReturn(Mono.just(article))

        val result = articleRepositoryCustom.relateAuthor(authorRelation)

        verify(this.articleRepository, atLeastOnce()).findById(article.getGeneratedId())
        verify(this.authorRepository, atLeastOnce()).findById(author.getGeneratedId())

        StepVerifier
            .create(result)
            .expectNext(article)
            .verifyComplete()
    }

    @Test
    fun `Should return error when relate author is null`() {
        val article = Article(id = "N", name = "name", articleType = ArticleType.ARTICLE)
        val author = Author(id = "P", name = "Pedro")
        val authorRelation = AuthorRelation(articleId = article.getGeneratedId(), authorId = author.getGeneratedId())
        whenever(this.articleRepository.findById(article.getGeneratedId())).thenReturn(Mono.just(article))
        whenever(this.authorRepository.findById(author.getGeneratedId())).thenReturn(Mono.empty())

        val result = articleRepositoryCustom.relateAuthor(authorRelation)

        verify(this.articleRepository, atLeastOnce()).findById(article.getGeneratedId())
        verify(this.authorRepository, atLeastOnce()).findById(author.getGeneratedId())

        StepVerifier
            .create(result)
            .expectError(ResponseStatusException::class.java)
            .verify()
    }

    @Test
    fun `Should return error when article relate author is null`() {
        val article = Article(id = "N", name = "name", articleType = ArticleType.ARTICLE)
        val author = Author(id = "P", name = "Pedro")
        val authorRelation = AuthorRelation(articleId = article.getGeneratedId(), authorId = author.getGeneratedId())
        whenever(this.articleRepository.findById(article.getGeneratedId())).thenReturn(Mono.empty())
        whenever(this.authorRepository.findById(author.getGeneratedId())).thenReturn(Mono.just(author))

        val result = articleRepositoryCustom.relateAuthor(authorRelation)

        verify(this.articleRepository, atLeastOnce()).findById(article.getGeneratedId())
        verify(this.authorRepository, atLeastOnce()).findById(author.getGeneratedId())

        StepVerifier
            .create(result)
            .expectError(ResponseStatusException::class.java)
            .verify()
    }

    @Test
    fun `Should relate category`() {
        val article = Article(id = "N", name = "name", articleType = ArticleType.ARTICLE)
        val category = Category(id = "R", name = "Refactoring")
        val categoryRelation = CategoryRelation(
            articleId = article.getGeneratedId(), categoryId = category.getGeneratedId()
        )
        whenever(this.articleRepository.findById(article.getGeneratedId())).thenReturn(Mono.just(article))
        whenever(this.categoryRepository.findById(category.getGeneratedId())).thenReturn(Mono.just(category))
        whenever(this.articleRepository.save(article)).thenReturn(Mono.just(article))

        val result = articleRepositoryCustom.relateCategory(categoryRelation)

        verify(this.articleRepository, atLeastOnce()).findById(article.getGeneratedId())
        verify(this.categoryRepository, atLeastOnce()).findById(category.getGeneratedId())
        StepVerifier
            .create(result)
            .expectNext(article)
            .verifyComplete()
    }

    @Test
    fun `Should return error when article relate category`() {
        val article = Article(id = "N", name = "name", articleType = ArticleType.ARTICLE)
        val category = Category(id = "R", name = "Refactoring")
        val categoryRelation = CategoryRelation(
            articleId = article.getGeneratedId(), categoryId = category.getGeneratedId()
        )
        whenever(this.articleRepository.findById(article.getGeneratedId())).thenReturn(Mono.empty())
        whenever(this.categoryRepository.findById(category.getGeneratedId())).thenReturn(Mono.just(category))

        val result = articleRepositoryCustom.relateCategory(categoryRelation)

        verify(this.articleRepository, atLeastOnce()).findById(article.getGeneratedId())
        verify(this.categoryRepository, atLeastOnce()).findById(category.getGeneratedId())
        StepVerifier
            .create(result)
            .expectError(ResponseStatusException::class.java)
            .verify()
    }

    @Test
    fun `Should return error when relate category`() {
        val article = Article(id = "N", name = "name", articleType = ArticleType.ARTICLE)
        val category = Category(id = "R", name = "Refactoring")
        val categoryRelation = CategoryRelation(
            articleId = article.getGeneratedId(), categoryId = category.getGeneratedId()
        )
        whenever(this.articleRepository.findById(article.getGeneratedId())).thenReturn(Mono.just(article))
        whenever(this.categoryRepository.findById(category.getGeneratedId())).thenReturn(Mono.empty())

        val result = articleRepositoryCustom.relateCategory(categoryRelation)

        verify(this.articleRepository, atLeastOnce()).findById(article.getGeneratedId())
        verify(this.categoryRepository, atLeastOnce()).findById(category.getGeneratedId())
        StepVerifier
            .create(result)
            .expectError(ResponseStatusException::class.java)
            .verify()
    }

    @Test
    fun `Should relate keyword`() {
        val article = Article(id = "N", name = "name", articleType = ArticleType.ARTICLE)
        val keyword = Keyword("Refactoring", name = "Refactoring")
        val keywordRelation = KeywordRelation(articleId = article.getGeneratedId(), keywordName = keyword.name!!)
        whenever(this.articleRepository.findById(article.getGeneratedId())).thenReturn(Mono.just(article))
        whenever(this.keyWordRepository.findById(keyword.name!!)).thenReturn(Mono.just(keyword))
        whenever(this.articleRepository.save(article)).thenReturn(Mono.just(article))

        val result = articleRepositoryCustom.relateKeyword(keywordRelation)

        verify(this.articleRepository, atLeastOnce()).findById(article.getGeneratedId())
        verify(this.keyWordRepository, atLeastOnce()).findById(keyword.name!!)
        StepVerifier
            .create(result)
            .expectNext(article)
            .verifyComplete()
    }

    @Test
    fun `Should return error when relate keyword`() {
        val article = Article(id = "N", name = "name", articleType = ArticleType.ARTICLE)
        val keyword = Keyword("Refactoring", name = "Refactoring")
        val keywordRelation = KeywordRelation(articleId = article.getGeneratedId(), keywordName = keyword.name!!)
        whenever(this.articleRepository.findById(article.getGeneratedId())).thenReturn(Mono.just(article))
        whenever(this.keyWordRepository.findById(keyword.name!!)).thenReturn(Mono.empty())

        val result = articleRepositoryCustom.relateKeyword(keywordRelation)

        verify(this.articleRepository, atLeastOnce()).findById(article.getGeneratedId())
        verify(this.keyWordRepository, atLeastOnce()).findById(keyword.name!!)
        StepVerifier
            .create(result)
            .expectError(ResponseStatusException::class.java)
            .verify()
    }

    @Test
    fun `Should return error when article relate keyword`() {
        val article = Article(id = "N", name = "name", articleType = ArticleType.ARTICLE)
        val keyword = Keyword("Refactoring", name = "Refactoring")
        val keywordRelation = KeywordRelation(articleId = article.getGeneratedId(), keywordName = keyword.name!!)
        whenever(this.articleRepository.findById(article.getGeneratedId())).thenReturn(Mono.empty())
        whenever(this.keyWordRepository.findById(keyword.name!!)).thenReturn(Mono.just(keyword))

        val result = articleRepositoryCustom.relateKeyword(keywordRelation)

        verify(this.articleRepository, atLeastOnce()).findById(article.getGeneratedId())
        verify(this.keyWordRepository, atLeastOnce()).findById(keyword.name!!)
        StepVerifier
            .create(result)
            .expectError(ResponseStatusException::class.java)
            .verify()
    }

    @Test
    fun `Should relate publication medium`() {
        val article = Article(id = "N", name = "name", articleType = ArticleType.ARTICLE)
        val publicationMedium = PublicationMedium(id = "P", name = "Medium", publisherType = PublisherType.JOURNAL)
        val publicationMediumRelation = PublicationMediumRelation(
            articleId = article.getGeneratedId(), publicationMediumId = publicationMedium.getGeneratedId()
        )
        whenever(this.articleRepository.findById(article.getGeneratedId())).thenReturn(Mono.just(article))
        whenever(this.publicationMediumRepository.findById(publicationMedium.getGeneratedId()))
            .thenReturn(Mono.just(publicationMedium))
        whenever(this.articleRepository.save(article)).thenReturn(Mono.just(article))

        val result = articleRepositoryCustom.relatePublicationMedium(publicationMediumRelation)

        verify(this.articleRepository, atLeastOnce()).findById(article.getGeneratedId())
        verify(this.publicationMediumRepository, atLeastOnce()).findById(publicationMedium.getGeneratedId())
        StepVerifier
            .create(result)
            .expectNext(article)
            .verifyComplete()
    }

    @Test
    fun `Should return error when relate publication medium`() {
        val article = Article(id = "N", name = "name", articleType = ArticleType.ARTICLE)
        val publicationMedium = PublicationMedium(id = "P", name = "Medium", publisherType = PublisherType.JOURNAL)
        val publicationMediumRelation = PublicationMediumRelation(
            articleId = article.getGeneratedId(), publicationMediumId = publicationMedium.getGeneratedId()
        )
        whenever(this.articleRepository.findById(article.getGeneratedId())).thenReturn(Mono.just(article))
        whenever(this.publicationMediumRepository.findById(publicationMedium.getGeneratedId())).thenReturn(Mono.empty())

        val result = articleRepositoryCustom.relatePublicationMedium(publicationMediumRelation)

        verify(this.articleRepository, atLeastOnce()).findById(article.getGeneratedId())
        verify(this.publicationMediumRepository, atLeastOnce()).findById(publicationMedium.getGeneratedId())
        StepVerifier
            .create(result)
            .expectError(ResponseStatusException::class.java)
            .verify()
    }

    @Test
    fun `Should return error when relate article publication medium`() {
        val article = Article(id = "N", name = "name", articleType = ArticleType.ARTICLE)
        val publicationMedium = PublicationMedium(id = "P", name = "Medium", publisherType = PublisherType.JOURNAL)
        val publicationMediumRelation = PublicationMediumRelation(
            articleId = article.getGeneratedId(), publicationMediumId = publicationMedium.getGeneratedId()
        )
        whenever(this.articleRepository.findById(article.getGeneratedId())).thenReturn(Mono.empty())
        whenever(this.publicationMediumRepository.findById(publicationMedium.getGeneratedId()))
            .thenReturn(Mono.just(publicationMedium))

        val result = articleRepositoryCustom.relatePublicationMedium(publicationMediumRelation)

        verify(this.articleRepository, atLeastOnce()).findById(article.getGeneratedId())
        verify(this.publicationMediumRepository, atLeastOnce()).findById(publicationMedium.getGeneratedId())
        StepVerifier
            .create(result)
            .expectError(ResponseStatusException::class.java)
            .verify()
    }
}
