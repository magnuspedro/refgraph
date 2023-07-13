package com.magnuspedro.refgraph.entities.requests

import jakarta.validation.constraints.NotEmpty

data class KeywordRequest(
    @field:NotEmpty
    val name: String
)
