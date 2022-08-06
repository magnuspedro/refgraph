package com.magnuspedro.refgraph.entities.requests

import javax.validation.constraints.NotEmpty

data class KeywordRequest(
    @field:NotEmpty
    val name: String
)
