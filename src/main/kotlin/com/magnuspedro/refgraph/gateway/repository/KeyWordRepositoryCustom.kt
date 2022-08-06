package com.magnuspedro.refgraph.gateway.repository

import com.magnuspedro.refgraph.entities.vertices.Keyword
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Repository
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
class KeyWordRepositoryCustom(private val repository: KeyWordRepository) {
    fun save(keyword: Keyword): Mono<Keyword> {
        return this.repository.findById(keyword.name!!).flatMap<Keyword?> {
            Mono.error(ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Keyword already exists"))
        }.switchIfEmpty(this.repository.save(keyword))
    }

    fun findAll(): Flux<Keyword> {
        return repository.findAll()
    }
}