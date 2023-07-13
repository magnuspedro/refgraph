package com.magnuspedro.refgraph.entities.requests

import java.time.LocalDate
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class PublicationMediumRelation(
    @field:NotEmpty
    val articleId: String,
    @field:NotEmpty
    val publicationMediumId: String,
    val version: Long? = null,
    val volume: Long? = null,
    val issue: Long? = null,
    @field:NotNull
    val date: LocalDate? = null
)