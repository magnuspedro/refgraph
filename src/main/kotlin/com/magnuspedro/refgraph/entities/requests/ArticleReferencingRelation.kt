package com.magnuspedro.refgraph.entities.requests

import jakarta.validation.constraints.NotEmpty

data class ArticleReferencingRelation(
    @field:NotEmpty
    val mainArticleId: String,
    @field:NotEmpty
    val referencingArticleId: String
)
