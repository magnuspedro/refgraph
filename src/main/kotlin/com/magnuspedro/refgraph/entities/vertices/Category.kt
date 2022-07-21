package com.magnuspedro.refgraph.entities.vertices

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import java.util.UUID

data class Category(
    @Id
    @GeneratedValue
    @field:Schema(hidden = true)
    val id: UUID? = null,
    val name: String? = null
)
