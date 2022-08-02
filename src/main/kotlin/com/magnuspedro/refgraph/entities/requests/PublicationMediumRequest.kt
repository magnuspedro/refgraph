package com.magnuspedro.refgraph.entities.requests

import com.magnuspedro.refgraph.entities.vertices.enums.PublisherType

data class PublicationMediumRequest(
    val name: String? = null,
    val initials: String? = null,
    val issn: String? = null,
    val publisherType: PublisherType? = null
)