package com.magnuspedro.refgraph.gateway.rest

import com.magnuspedro.refgraph.entities.requests.PublicationMediumRequest
import com.magnuspedro.refgraph.entities.vertices.PublicationMedium
import com.magnuspedro.refgraph.gateway.repository.PublicationMediumRepository
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.apache.commons.text.WordUtils
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1/refgraph/publication-medium")
class PublicationMediumController(
    private val publicationMediumRepository: PublicationMediumRepository
) {

    @PostMapping
    @Operation(summary = "Create Publication medium", security = [SecurityRequirement(name = "bearerAuth")])
    fun createPublicationMedium(@RequestBody publicationMediumRequest: PublicationMediumRequest): Mono<PublicationMedium> {
        val publicationMedium = PublicationMedium(
            name = publicationMediumRequest.name,
            code = WordUtils.initials(publicationMediumRequest.name).uppercase(),
            initials = publicationMediumRequest.initials,
            issn = publicationMediumRequest.issn,
            publisherType = publicationMediumRequest.publisherType
        )
        return this.publicationMediumRepository.save(publicationMedium)
    }

    @GetMapping
    @Operation(summary = "Find all publication medium", security = [SecurityRequirement(name = "bearerAuth")])
    fun findAllPublicationMedium(): Flux<PublicationMedium> {
        return this.publicationMediumRepository.findAll()
    }
}