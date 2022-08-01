package com.magnuspedro.refgraph.entities.edges

import com.magnuspedro.refgraph.entities.vertices.PublicationMedium
import org.springframework.data.neo4j.core.schema.RelationshipId
import org.springframework.data.neo4j.core.schema.TargetNode

data class CitationMedium(
    @RelationshipId
    val id: Long,
    @TargetNode
    val conference: PublicationMedium? = null,
    val version: Long? = null,
    val volume: Long? = null,
    val issue: Long? = null
)
