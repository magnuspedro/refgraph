package com.magnuspedro.refgraph.gateway.repository

import com.magnuspedro.refgraph.entities.vertices.Author
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

internal class AuthorRepositoryCustomTest {

    private val repository: AuthorRepository = mock()
    private lateinit var authorRepositoryCustom: AuthorRepositoryCustom

    @BeforeEach
    fun setup() {
        authorRepositoryCustom = AuthorRepositoryCustom(repository)
    }

    @Test
    fun `Should save a publication medium`() {
        val author = Author(id = "N", name = "name")
        whenever(repository.findById(author.getGeneratedId())).thenReturn(Mono.empty())
        whenever(repository.save(author)).thenReturn(Mono.just(author))

        val result = authorRepositoryCustom.save(author)

        verify(repository, atLeastOnce()).findById(author.getGeneratedId())
        verify(repository, atLeastOnce()).save(author)
        StepVerifier
            .create(result)
            .expectNextMatches {
                it.id == author.id
                        && it.name == author.name
            }
            .verifyComplete()
    }

    @Test
    fun `Should throws that publication medium already exists`() {
        val author = Author(id = "N", name = "name")
        whenever(repository.findById(author.getGeneratedId())).thenReturn(Mono.just(author))
        whenever(repository.save(author)).thenReturn(Mono.just(author))

        val result = authorRepositoryCustom.save(author)

        verify(repository, atLeastOnce()).findById(author.getGeneratedId())
        StepVerifier
            .create(result)
            .expectError(ResponseStatusException::class.java)
            .verify()
    }

    @Test
    fun `Should return all publication medium`() {
        val author = Author(id = "N", name = "name")
        whenever(repository.findAll()).thenReturn(Flux.just(author))

        val result = authorRepositoryCustom.findAll()

        verify(repository, atLeastOnce()).findAll()
        StepVerifier
            .create(result)
            .expectNextMatches {
                it.id == author.id
                        && it.name == author.name
            }
            .verifyComplete()
    }
}