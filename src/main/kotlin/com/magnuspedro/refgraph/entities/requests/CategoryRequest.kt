package com.magnuspedro.refgraph.entities.requests

import jakarta.validation.constraints.NotEmpty

data class CategoryRequest(
    @field:NotEmpty
    val name: String
)
