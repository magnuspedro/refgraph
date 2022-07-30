package com.magnuspedro.refgraph.entities.requests

import java.time.LocalDate

data class ThesisRequest(
    val title: String? = null,
    val school: String? = null,
    val date: LocalDate? = null,
    val code: String? = null
)