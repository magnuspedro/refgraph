package com.magnuspedro.refgraph.entities.requests

import java.time.LocalDate

data class PublicationMediumRelation(
    val articleCode: String? = null,
    val publicationMediumCode: String? = null,
    val version: Long? = null,
    val volume: Long? = null,
    val issue: Long? = null,
    val date: LocalDate? = null
)