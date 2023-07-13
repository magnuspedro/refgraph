package com.magnuspedro.refgraph.entities.requests

import jakarta.validation.constraints.NotEmpty

data class KeywordRelation(
    @field:NotEmpty
    val keywordName: String,
    @field:NotEmpty
    val articleId: String
)
