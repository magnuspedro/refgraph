package com.magnuspedro.refgraph.entities.vertices

import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node
import java.util.*

@Node
data class Keyword(
    @Id
    @GeneratedValue
    val id: UUID? = null,
    val name: String? = null,
)
