package com.magnuspedro.refgraph.extensions

import com.magnuspedro.refgraph.entities.vertices.Category
import com.magnuspedro.refgraph.extensions.Extensions.Companion.abbrv
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono
import reactor.test.StepVerifier


internal class ExtensionsTest {

    @Test
    fun `Should return error because mono is null`() {
        val result = Extensions.verifyNull<Category>(Mono.empty(), "null")

        StepVerifier.create(result)
            .expectError(ResponseStatusException::class.java)
            .verify()
    }


    @Test
    fun `Should return mono because it is not null`() {
        val category = Category()
        val mono = Mono.just(category)
        val result = Extensions.verifyNull<Category>(mono, "null")

        StepVerifier.create(result)
            .expectNext(category)
            .verifyComplete()
    }

    @Test
    fun `Should return abbreviated string`() {
       val abbrv = "Pedro".abbrv()

        Assertions.assertEquals(abbrv,"PE")
    }
}