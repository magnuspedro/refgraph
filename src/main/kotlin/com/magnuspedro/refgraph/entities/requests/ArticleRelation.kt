package com.magnuspedro.refgraph.entities.requests

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class ArticleRelation(
    @field:NotNull
    @field:NotEmpty
    val firstArticleCode: String,
    @field:NotNull
    @field:NotEmpty
    val secondArticleCode: String
)
