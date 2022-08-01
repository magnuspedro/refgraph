package com.magnuspedro.refgraph.entities.requests

import com.magnuspedro.refgraph.entities.requests.enums.ArticleType
import java.time.LocalDate

data class ArticleRequest(
    val title: String? = null,
    val date: LocalDate? = null,
    val pages: String? = null,
    val doi: String? = null,
    val edition: Long? = null,
    val publisher: String? = null,
    val bookTitle: String? = null,
    val school: String? = null,
    val articleType: ArticleType? = null
)