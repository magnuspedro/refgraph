package com.magnuspedro.refgraph.entities.requests

import java.time.LocalDate

data class InProceedingsRequest(
    val title: String? = null,
    val date: LocalDate? = null,
    val bookTitle: String? = null,
    val code: String? = null
)