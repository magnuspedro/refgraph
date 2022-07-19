package com.magnuspedro.refgraph.entities.requests

import com.magnuspedro.refgraph.entities.vertices.BaseArticle
import java.util.UUID

data class CreateAndRelateArticle(
    val article: BaseArticle? = null,
    val citation: Set<Citation>? = null,
    val categoryId: Set<UUID>? = null,
    val authorIds: Set<UUID>? = null,
    val publishers: Publisher? = null
)

