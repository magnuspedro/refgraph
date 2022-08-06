package com.magnuspedro.refgraph.gateway.repository

import com.magnuspedro.refgraph.entities.vertices.Author
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Repository
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
class AuthorRepositoryCustom(private val repository: AuthorRepository) {
    fun save(author: Author): Mono<Author> {
        return repository.findById(author.getGeneratedId()).flatMap<Author?> {
            Mono.error(ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Author already exists"))
        }.switchIfEmpty(repository.save(author))
    }

    fun findAll(): Flux<Author> {
        return repository.findAll()
    }
}