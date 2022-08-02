package com.magnuspedro.refgraph.gateway.repository

import com.magnuspedro.refgraph.entities.vertices.PublicationMedium
import org.springframework.data.neo4j.core.ReactiveNeo4jTemplate
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono

class PublicationMediumRepositoryCustomImpl(private val neo4jTemplate: ReactiveNeo4jTemplate) :
    PublicationMediumRepositoryCustom {
    override fun save(publicationMedium: PublicationMedium): Mono<PublicationMedium> {
        return findByCode(publicationMedium.code).flatMap<PublicationMedium?> {
            Mono.error(ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Publication Medium already exists"))
        }.switchIfEmpty(neo4jTemplate.save(publicationMedium))
    }

    override fun findByCode(code: String?): Mono<PublicationMedium> {
        return neo4jTemplate.findOne(
            "MATCH (PublicationMedium:PublicationMedium {code: \$code}) RETURN PublicationMedium",
            mapOf(Pair("code", code)),
            PublicationMedium::class.java
        )
    }
}