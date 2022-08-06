package com.magnuspedro.refgraph.entities.requests

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class CategoryRequest(
    @field:NotNull
    @field:NotEmpty
    val name: String
)
