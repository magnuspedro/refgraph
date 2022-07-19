package com.magnuspedro.refgraph.entities.vertices

import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Relationship
import org.springframework.data.neo4j.core.schema.Relationship.Direction.INCOMING
import org.springframework.data.neo4j.core.schema.Relationship.Direction.OUTGOING
import java.time.LocalDate
import java.util.UUID

@Node
data class Thesis(
    @Id
    @GeneratedValue
    override val id: UUID? = null,
    override val name: String? = null,
    val school: String? = null,
    override val date: LocalDate? = null,
    @Relationship(type = "CITED", direction = INCOMING)
    override var cited: MutableList<BaseArticle>? = mutableListOf(),
    @Relationship(type = "REFERENCED", direction = OUTGOING)
    override var referenced: MutableList<BaseArticle>? = mutableListOf(),
    @Relationship(type = "WROTE")
    override var wrote: MutableList<Author>? = mutableListOf(),
    @Relationship(type = "PUBLISHED", direction = INCOMING)
    override var published: PublicationMedium? = null,
    @Relationship(type = "CATEGORY", direction = INCOMING)
    override var category: Category? = null
) : BaseArticle(id, name, date, cited, referenced, wrote, published, category)
