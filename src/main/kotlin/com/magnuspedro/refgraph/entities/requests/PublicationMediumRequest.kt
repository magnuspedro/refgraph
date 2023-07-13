package com.magnuspedro.refgraph.entities.requests

import com.magnuspedro.refgraph.entities.vertices.enums.PublisherType
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class PublicationMediumRequest(
    @field:NotEmpty
    val name: String,
    val initials: String? = null,
    val issn: String? = null,
    @field:NotNull
    val publisherType: PublisherType
)