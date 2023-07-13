package com.magnuspedro.refgraph.entities.requests

import jakarta.validation.constraints.NotEmpty

data class AuthorRelation(
    @field:NotEmpty
    val articleId: String,
    @field:NotEmpty
    val authorId: String
)
