package com.magnuspedro.refgraph.entities.requests

import jakarta.validation.constraints.NotEmpty

data class ArticleCitationRelation(
    @field:NotEmpty
    val mainArticleId: String,
    @field:NotEmpty
    val citedArticleId: String
)
