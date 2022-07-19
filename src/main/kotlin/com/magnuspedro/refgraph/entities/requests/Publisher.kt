package com.magnuspedro.refgraph.entities.requests

import com.magnuspedro.refgraph.entities.requests.enums.ArticleType
import com.magnuspedro.refgraph.entities.requests.enums.ReferenceType
import com.magnuspedro.refgraph.entities.vertices.enums.PublisherType
import java.util.UUID

data class Publisher(
    val id: UUID? = null,
    val type: PublisherType? = null,
)