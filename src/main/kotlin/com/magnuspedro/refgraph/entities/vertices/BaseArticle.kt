package com.magnuspedro.refgraph.entities.vertices

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Relationship
import reactor.core.publisher.Mono
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
        @Schema(hidden = true)
        id: UUID?,
        name: String?,
        date: LocalDate?,
        cited: MutableList<BaseArticle>?,
        referenced: MutableList<BaseArticle>?,
        wrote: List<Author>?,
        published: PublicationMedium?,
        category: Category?
    )

    abstract val id: UUID?
    abstract val name: String?
    abstract val date: LocalDate?
    abstract var cited: MutableList<BaseArticle>?
    abstract var referenced: MutableList<BaseArticle>?
    abstract var wrote: MutableList<Author>?
    abstract var published: PublicationMedium?
    abstract var category: Category?
}
