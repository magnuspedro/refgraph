package com.magnuspedro.refgraph.entities.requests

import javax.validation.constraints.NotEmpty

data class AuthorRequest(
    @field:NotEmpty
    val name: String
)
