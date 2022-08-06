package com.magnuspedro.refgraph.gateway.repository

import com.magnuspedro.refgraph.entities.vertices.PublicationMedium
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Repository
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
class PublicationMediumRepositoryCustom(private val repository: PublicationMediumRepository) {
    fun save(publicationMedium: PublicationMedium): Mono<PublicationMedium> {
        return repository.findById(publicationMedium.getGeneratedId()).flatMap<PublicationMedium?> {
            Mono.error(ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Publication Medium already exists"))
        }.switchIfEmpty(repository.save(publicationMedium))
    }

    fun findAll(): Flux<PublicationMedium> {
        return repository.findAll()
    }
}