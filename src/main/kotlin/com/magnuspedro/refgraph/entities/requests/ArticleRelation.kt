package com.magnuspedro.refgraph.entities.requests

import javax.validation.constraints.NotEmpty

data class ArticleRelation(
    @field:NotEmpty
    val firstArticleCode: String,
    @field:NotEmpty
    val secondArticleCode: String
)
