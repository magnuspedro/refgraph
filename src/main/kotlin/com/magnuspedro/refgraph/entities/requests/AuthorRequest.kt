package com.magnuspedro.refgraph.entities.requests

import jakarta.validation.constraints.NotEmpty

data class AuthorRequest(
    @field:NotEmpty
    val name: String
)
