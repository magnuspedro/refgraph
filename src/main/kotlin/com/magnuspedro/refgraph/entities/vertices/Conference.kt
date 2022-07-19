package com.magnuspedro.refgraph.entities.vertices

import com.magnuspedro.refgraph.entities.vertices.enums.PublisherType
import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node
import java.time.LocalDate
import java.util.UUID

@Node
data class Conference(
    @Id
    @GeneratedValue
    val id: UUID? = null,
    val name: String? = null,
    val initials: String? = null,
    val date: LocalDate? = null,
    val version: Long? = null,
) : PublicationMedium(id, name, date)