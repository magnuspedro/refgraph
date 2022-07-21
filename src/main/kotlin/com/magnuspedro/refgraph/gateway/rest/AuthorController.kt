package com.magnuspedro.refgraph.gateway.rest

import com.magnuspedro.refgraph.entities.vertices.Article
import com.magnuspedro.refgraph.entities.vertices.Author
import com.magnuspedro.refgraph.entities.vertices.InProceedings
import com.magnuspedro.refgraph.gateway.repository.AuthorRepository
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1/refgraph//author")
class AuthorController(
    val authorRepository: AuthorRepository
) {

    @PostMapping
    fun createAuthor(@RequestBody author: Author): Mono<Author> {
        return this.authorRepository.save(author)
    }

    @GetMapping
    fun findAllAuthors(): Flux<Author> {
        return this.authorRepository.findAll()
    }
}