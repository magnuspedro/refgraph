package com.magnuspedro.refgraph.entities.vertices

import com.fasterxml.jackson.annotation.JsonIgnore
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node
import java.util.UUID

@Node
data class Author(
    @Id
    @GeneratedValue
    val id: UUID? = null,
    val name: String? = null,
    val code: String? = null
)