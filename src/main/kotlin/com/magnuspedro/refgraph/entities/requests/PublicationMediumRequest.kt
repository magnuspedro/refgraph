package com.magnuspedro.refgraph.entities.requests

import com.magnuspedro.refgraph.entities.vertices.enums.PublisherType
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class PublicationMediumRequest(
    @field:NotNull
    @field:NotEmpty
    val name: String,
    val initials: String? = null,
    val issn: String? = null,
    @field:NotNull
    val publisherType: PublisherType
)