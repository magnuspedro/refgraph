package com.magnuspedro.refgraph.entities.requests

import java.time.LocalDate

data class BookRequest(
    val title: String? = null,
    val date: LocalDate? = null,
    val edition: Long? = null,
    val publisher: String? = null,
    val code: String? = null
)
