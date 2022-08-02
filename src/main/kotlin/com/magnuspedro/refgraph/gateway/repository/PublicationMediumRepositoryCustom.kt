package com.magnuspedro.refgraph.gateway.repository

import com.magnuspedro.refgraph.entities.vertices.PublicationMedium
import reactor.core.publisher.Mono

interface PublicationMediumRepositoryCustom {
    fun save(publicationMedium: PublicationMedium): Mono<PublicationMedium>
    fun findByCode(code: String?): Mono<PublicationMedium>
}