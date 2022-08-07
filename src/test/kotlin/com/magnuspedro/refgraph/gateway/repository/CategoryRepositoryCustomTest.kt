package com.magnuspedro.refgraph.gateway.repository

import com.magnuspedro.refgraph.entities.vertices.Category
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

internal class CategoryRepositoryCustomTest {

    private val repository: CategoryRepository = mock()
    private lateinit var categoryRepositoryCustom: CategoryRepositoryCustom

    @BeforeEach
    fun setup() {
        categoryRepositoryCustom = CategoryRepositoryCustom(repository)
    }

    @Test
    fun `Should save a publication medium`() {
        val category = Category(id = "N", name = "name")
        whenever(repository.findById(category.getGeneratedId())).thenReturn(Mono.empty())
        whenever(repository.save(category)).thenReturn(Mono.just(category))

        val result = categoryRepositoryCustom.save(category)

        verify(repository, atLeastOnce()).findById(category.getGeneratedId())
        verify(repository, atLeastOnce()).save(category)
        StepVerifier
            .create(result)
            .expectNext(category)
            .verifyComplete()
    }

    @Test
    fun `Should throws that publication medium already exists`() {
        val category = Category(id = "N", name = "name")
        whenever(repository.findById(category.getGeneratedId())).thenReturn(Mono.just(category))
        whenever(repository.save(category)).thenReturn(Mono.just(category))

        val result = categoryRepositoryCustom.save(category)

        verify(repository, atLeastOnce()).findById(category.getGeneratedId())
        StepVerifier
            .create(result)
            .expectError(ResponseStatusException::class.java)
            .verify()
    }

    @Test
    fun `Should return all publication medium`() {
        val category = Category(id = "N", name = "name")
        whenever(repository.findAll()).thenReturn(Flux.just(category))

        val result = categoryRepositoryCustom.findAll()

        verify(repository, atLeastOnce()).findAll()
        StepVerifier
            .create(result)
            .expectNext(category)
            .verifyComplete()
    }
}