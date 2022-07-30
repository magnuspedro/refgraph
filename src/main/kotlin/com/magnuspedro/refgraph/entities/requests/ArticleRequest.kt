package com.magnuspedro.refgraph.entities.requests

import java.time.LocalDate

data class ArticleRequest(
    val title: String? = null,
    val date: LocalDate? = null,
    val pages: String? = null,
    val doi: String? = null,
    val code: String? = null
)