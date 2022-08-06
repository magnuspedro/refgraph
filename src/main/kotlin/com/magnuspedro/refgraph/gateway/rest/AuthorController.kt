package com.magnuspedro.refgraph.gateway.rest

import com.magnuspedro.refgraph.entities.requests.AuthorRequest
import com.magnuspedro.refgraph.entities.vertices.Author
import com.magnuspedro.refgraph.gateway.repository.AuthorRepositoryCustom
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/refgraph/author")
class AuthorController(
    private val authorRepository: AuthorRepositoryCustom
) {

    @Operation(summary = "Create author", security = [SecurityRequirement(name = "bearerAuth")])
    @PostMapping
    fun createAuthor(@RequestBody @Valid authorRequest: AuthorRequest): Mono<Author> {
        val author = Author(
            name = authorRequest.name,
        )
        return this.authorRepository.save(author)
    }


    @Operation(summary = "Find all authors", security = [SecurityRequirement(name = "bearerAuth")])
    @GetMapping
    fun findAllAuthors(): Flux<Author> {
        return this.authorRepository.findAll()
    }
}