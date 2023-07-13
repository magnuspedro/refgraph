package com.magnuspedro.refgraph.entities.requests

import com.magnuspedro.refgraph.entities.requests.enums.ArticleType
import jakarta.validation.constraints.NotEmpty
import java.time.LocalDate
import jakarta.validation.constraints.NotNull

data class ArticleRequest(
    @field:NotEmpty
    val title: String,
    @field:NotNull
    val date: LocalDate,
    val pages: String? = null,
    val doi: String? = null,
    val edition: Long? = null,
    val publisher: String? = null,
    val bookTitle: String? = null,
    val school: String? = null,
    @field:NotNull
    val articleType: ArticleType
)