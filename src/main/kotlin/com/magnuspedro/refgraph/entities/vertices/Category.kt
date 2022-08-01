package com.magnuspedro.refgraph.entities.vertices

import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import java.util.UUID

data class Category(
    @Id
    @GeneratedValue
    val id: UUID? = null,
    val name: String? = null,
    var code: String? = null
)
