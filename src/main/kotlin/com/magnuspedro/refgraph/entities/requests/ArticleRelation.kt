package com.magnuspedro.refgraph.entities.requests

import com.magnuspedro.refgraph.entities.requests.enums.ArticleType

data class ArticleRelation(
    val firstArticleCode: String? = null,
    val secondArticleCode: String? = null
)
