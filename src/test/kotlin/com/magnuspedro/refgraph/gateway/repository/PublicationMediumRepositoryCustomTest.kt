package com.magnuspedro.refgraph.gateway.repository

import com.magnuspedro.refgraph.entities.vertices.PublicationMedium
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

internal class PublicationMediumRepositoryCustomTest {
    private val repository: PublicationMediumRepository = mock()
    private lateinit var publicationMediumRepositoryCustom: PublicationMediumRepositoryCustom

    @BeforeEach
    fun setup() {
        publicationMediumRepositoryCustom = PublicationMediumRepositoryCustom(repository)
    }

    @Test
    fun `Should save a publication medium`() {
        val publicationMedium = PublicationMedium(id = "N", name = "name", publisherType = PublisherType.JOURNAL)
        whenever(repository.findById(publicationMedium.getGeneratedId())).thenReturn(Mono.empty())
        whenever(repository.save(publicationMedium)).thenReturn(Mono.just(publicationMedium))

        val result = publicationMediumRepositoryCustom.save(publicationMedium)

        verify(repository, atLeastOnce()).findById(publicationMedium.getGeneratedId())
        verify(repository, atLeastOnce()).save(publicationMedium)
        StepVerifier
            .create(result)
            .expectNextMatches {
                it.id == publicationMedium.id
                        && it.name == publicationMedium.name
                        && it.publisherType == publicationMedium.publisherType
            }
            .verifyComplete()
    }

    @Test
    fun `Should throws that publication medium already exists`() {
        val publicationMedium = PublicationMedium(id = "N", name = "name", publisherType = PublisherType.JOURNAL)
        whenever(repository.findById(publicationMedium.getGeneratedId())).thenReturn(Mono.just(publicationMedium))
        whenever(repository.save(publicationMedium)).thenReturn(Mono.just(publicationMedium))

        val result = publicationMediumRepositoryCustom.save(publicationMedium)

        verify(repository, atLeastOnce()).findById(publicationMedium.getGeneratedId())
        StepVerifier
            .create(result)
            .expectError(ResponseStatusException::class.java)
            .verify()
    }

    @Test
    fun `Should return all publication medium`() {
        val publicationMedium = PublicationMedium(id = "N", name = "name", publisherType = PublisherType.JOURNAL)
        whenever(repository.findAll()).thenReturn(Flux.just(publicationMedium))

        val result = publicationMediumRepositoryCustom.findAll()

        verify(repository, atLeastOnce()).findAll()
        StepVerifier
            .create(result)
            .expectNextMatches {
                it.id == publicationMedium.id
                        && it.name == publicationMedium.name
                        && it.publisherType == publicationMedium.publisherType
            }
            .verifyComplete()
    }
}