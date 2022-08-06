package com.magnuspedro.refgraph.entities.requests

import javax.validation.constraints.NotEmpty

data class KeywordRelation(
    @field:NotEmpty
    val keywordName: String,
    @field:NotEmpty
    val articleCode: String
)
