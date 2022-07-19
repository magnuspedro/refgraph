package com.magnuspedro.refgraph.entities.vertices

import com.magnuspedro.refgraph.entities.vertices.enums.PublisherType
import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node
import java.time.LocalDate
import java.util.UUID

@Node
data class Journal(
    @Id
    @GeneratedValue
    val id: UUID? = null,
    val name: String? = null,
    val date: LocalDate? = null,
    val volume: Long? = null,
    val issue: Long? = null,
    val issn: String? = null,
) : PublicationMedium(id, name, date)