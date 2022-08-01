package com.magnuspedro.refgraph.entities.vertices

import com.magnuspedro.refgraph.entities.edges.CitationMedium
import com.magnuspedro.refgraph.entities.requests.enums.ArticleType
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Relationship
import org.springframework.data.neo4j.core.schema.Relationship.Direction.INCOMING
import org.springframework.data.neo4j.core.schema.Relationship.Direction.OUTGOING
import java.time.LocalDate
import java.util.UUID

@Node
data class Article(
    @Id
    @GeneratedValue
    @Schema(hidden = true)
    val id: UUID? = null,
    val name: String? = null,
    val date: LocalDate? = null,
    val doi: String? = null,
    val pages: String? = null,
    val publisher: String? = null,
    val edition: Long? = null,
    val code: String? = null,
    val bookTitle: String? = null,
    val school: String? = null,
    val articleType: ArticleType? = null,
    @Relationship(type = "PUBLISHED", direction = INCOMING)
    var published: CitationMedium? = null,
    @Relationship(type = "CITED", direction = INCOMING)
    var cited: Article? = null,
    @Relationship(type = "REFERENCED", direction = OUTGOING)
    var referenced: Article? = null,
    @Relationship(type = "WROTE")
    var wrote: MutableList<Author>? = mutableListOf(),
    @Relationship(type = "CATEGORY", direction = INCOMING)
    var category: Category? = null,
)