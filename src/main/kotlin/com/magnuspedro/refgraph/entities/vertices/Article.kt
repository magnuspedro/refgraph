package com.magnuspedro.refgraph.entities.vertices

import com.fasterxml.jackson.annotation.JsonIgnore
import com.magnuspedro.refgraph.config.GenerateCode
import com.magnuspedro.refgraph.entities.edges.CitationMedium
import com.magnuspedro.refgraph.entities.requests.enums.ArticleType
import org.apache.commons.text.WordUtils
import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Relationship
import org.springframework.data.neo4j.core.schema.Relationship.Direction.INCOMING
import org.springframework.data.neo4j.core.schema.Relationship.Direction.OUTGOING
import java.time.LocalDate

@Node
data class Article(
    @Id
    @GeneratedValue(GenerateCode::class)
    val id: String? = null,
    val name: String? = null,
    val date: LocalDate? = null,
    val doi: String? = null,
    val pages: String? = null,
    val publisher: String? = null,
    val edition: Long? = null,
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
    var category: MutableList<Category>? = mutableListOf(),
    @Relationship(type = "KEYWORD", direction = INCOMING)
    var keyword: MutableList<Keyword>? = mutableListOf(),
) {
    @JsonIgnore
    fun getGeneratedId(): String {
        return WordUtils.initials(name).uppercase()
    }
}
