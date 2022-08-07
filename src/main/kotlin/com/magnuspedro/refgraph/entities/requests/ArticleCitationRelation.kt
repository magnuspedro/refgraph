package com.magnuspedro.refgraph.entities.requests

import javax.validation.constraints.NotEmpty

data class ArticleCitationRelation(
    @field:NotEmpty
    val mainArticleId: String,
    @field:NotEmpty
    val citedArticleId: String
)
