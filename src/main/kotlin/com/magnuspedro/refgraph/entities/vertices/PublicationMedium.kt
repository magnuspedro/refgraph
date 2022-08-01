package com.magnuspedro.refgraph.entities.vertices

import com.magnuspedro.refgraph.entities.vertices.enums.PublisherType
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node
import java.time.LocalDate
import java.util.UUID

@Node
data class PublicationMedium(
    @Id
    @GeneratedValue
    @Schema(hidden = true)
    val id: UUID? = null,
    val name: String? = null,
    val initials: String? = null,
    val date: LocalDate? = null,
    val code: String? = null,
    val issn: String? = null,
    val publisherType: PublisherType? = null
)