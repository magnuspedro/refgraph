package com.magnuspedro.refgraph.entities.requests

import javax.validation.constraints.NotEmpty

data class CategoryRelation(
    @field:NotEmpty
    val articleId: String,
    @field:NotEmpty
    val categoryId: String
)
