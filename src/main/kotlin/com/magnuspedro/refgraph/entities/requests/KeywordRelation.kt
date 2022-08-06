package com.magnuspedro.refgraph.entities.requests

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class KeywordRelation(
    @field:NotNull
    @field:NotEmpty
    val keywordName: String,
    @field:NotNull
    @field:NotEmpty
    val articleCode: String
)
