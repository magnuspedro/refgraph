package com.magnuspedro.refgraph.entities.requests

import com.magnuspedro.refgraph.entities.requests.enums.ArticleType
import java.time.LocalDate
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class ArticleRequest(
    @field:NotNull
    @field:NotEmpty
    val title: String,
    @field:NotNull
    @field:NotEmpty
    val date: LocalDate,
    val pages: String? = null,
    val doi: String? = null,
    val edition: Long? = null,
    val publisher: String? = null,
    val bookTitle: String? = null,
    val school: String? = null,
    @field:NotNull
    @field:NotEmpty
    val articleType: ArticleType
)