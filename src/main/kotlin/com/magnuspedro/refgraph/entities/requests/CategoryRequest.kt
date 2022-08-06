package com.magnuspedro.refgraph.entities.requests

import javax.validation.constraints.NotEmpty

data class CategoryRequest(
    @field:NotEmpty
    val name: String
)
