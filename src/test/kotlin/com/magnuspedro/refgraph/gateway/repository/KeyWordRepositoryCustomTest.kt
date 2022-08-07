package com.magnuspedro.refgraph.gateway.repository

import com.magnuspedro.refgraph.entities.vertices.Keyword
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

internal class KeyWordRepositoryCustomTest {
    private val repository: KeyWordRepository = mock()
    private lateinit var keyWordRepositoryCustom: KeyWordRepositoryCustom

    @BeforeEach
    fun setup() {
        keyWordRepositoryCustom = KeyWordRepositoryCustom(repository)
    }

    @Test
    fun `Should save a publication medium`() {
        val keyword = Keyword(id = "name", name = "name")
        whenever(repository.findById(keyword.name!!)).thenReturn(Mono.empty())
        whenever(repository.save(keyword)).thenReturn(Mono.just(keyword))

        val result = keyWordRepositoryCustom.save(keyword)

        verify(repository, atLeastOnce()).findById(keyword.name!!)
        verify(repository, atLeastOnce()).save(keyword)
        StepVerifier
            .create(result)
            .expectNextMatches {
                it.id == keyword.id
                        && it.name == keyword.name
            }
            .verifyComplete()
    }

    @Test
    fun `Should throws that publication medium already exists`() {
        val keyword = Keyword(id = "name", name = "name")
        whenever(repository.findById(keyword.name!!)).thenReturn(Mono.just(keyword))
        whenever(repository.save(keyword)).thenReturn(Mono.just(keyword))

        val result = keyWordRepositoryCustom.save(keyword)

        verify(repository, atLeastOnce()).findById(keyword.name!!)
        StepVerifier
            .create(result)
            .expectError(ResponseStatusException::class.java)
            .verify()
    }

    @Test
    fun `Should return all publication medium`() {
        val keyword = Keyword(id = "name", name = "name")
        whenever(repository.findAll()).thenReturn(Flux.just(keyword))

        val result = keyWordRepositoryCustom.findAll()

        verify(repository, atLeastOnce()).findAll()
        StepVerifier
            .create(result)
            .expectNextMatches {
                it.id == keyword.id
                        && it.name == keyword.name
            }
            .verifyComplete()
    }
}