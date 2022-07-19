package com.magnuspedro.refgraph.entities.requests

import com.magnuspedro.refgraph.entities.requests.enums.ArticleType
import com.magnuspedro.refgraph.entities.requests.enums.ReferenceType
import java.util.UUID

data class Citation(
    val id: UUID? = null,
    val type: ReferenceType? = null,
    val articleType: ArticleType? = null
)