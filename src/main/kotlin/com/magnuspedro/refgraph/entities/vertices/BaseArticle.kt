package com.magnuspedro.refgraph.entities.vertices

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import java.time.LocalDate
import java.util.UUID

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value = Article::class, name = "ARTICLE"),
    JsonSubTypes.Type(value = Book::class, name = "BOOK"),
    JsonSubTypes.Type(value = InProceedings::class, name = "INPROCEEDINGS"),
    JsonSubTypes.Type(value = Thesis::class, name = "THESIS")
)
abstract class BaseArticle {
    constructor(
        id: UUID?,
        title: String?,
        date: LocalDate?,
        cited: MutableList<BaseArticle>?,
        referenced: MutableList<BaseArticle>?,
        wrote: List<Author>?,
        published: PublicationMedium?,
        category: Category?)

    abstract val id: UUID?
    abstract val title: String?
    abstract val date: LocalDate?
    abstract var cited: MutableList<BaseArticle>?
    abstract var referenced: MutableList<BaseArticle>?
    abstract var wrote: MutableList<Author>?
    abstract var published: PublicationMedium?
    abstract var category: Category?
}
