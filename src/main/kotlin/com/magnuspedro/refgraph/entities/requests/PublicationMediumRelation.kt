package com.magnuspedro.refgraph.entities.requests

import java.time.LocalDate
import javax.validation.constraints.NotEmpty

data class PublicationMediumRelation(
    @field:NotEmpty
    val articleId: String,
    @field:NotEmpty
    val publicationMediumId: String,
    val version: Long? = null,
    val volume: Long? = null,
    val issue: Long? = null,
    val date: LocalDate? = null
)