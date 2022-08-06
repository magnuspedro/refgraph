package com.magnuspedro.refgraph.entities.vertices

import com.magnuspedro.refgraph.config.GenerateName
import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node

@Node
data class Keyword(
    @Id
    @GeneratedValue(GenerateName::class)
    val id: String? = null,
    val name: String? = null,
)
