package com.magnuspedro.refgraph.entities.requests

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class CategoryRelation(
    @field:NotNull
    @field:NotEmpty
    val articleId: String,
    @field:NotNull
    @field:NotEmpty
    val categoryId: String
)
