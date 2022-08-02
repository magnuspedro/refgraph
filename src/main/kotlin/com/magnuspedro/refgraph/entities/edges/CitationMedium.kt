package com.magnuspedro.refgraph.entities.edges

import com.magnuspedro.refgraph.entities.vertices.PublicationMedium
import org.springframework.data.neo4j.core.schema.RelationshipId
import org.springframework.data.neo4j.core.schema.RelationshipProperties
import org.springframework.data.neo4j.core.schema.TargetNode
import java.time.LocalDate

@RelationshipProperties
data class CitationMedium(
    @RelationshipId
    val id: Long? = null,
    @TargetNode
    val medium: PublicationMedium? = null,
    val version: Long? = null,
    val volume: Long? = null,
    val issue: Long? = null,
    val date: LocalDate? = null
)
