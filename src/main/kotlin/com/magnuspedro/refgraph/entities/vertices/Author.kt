package com.magnuspedro.refgraph.entities.vertices

import com.fasterxml.jackson.annotation.JsonIgnore
import com.magnuspedro.refgraph.config.GenerateCode
import com.magnuspedro.refgraph.extensions.Extensions.Companion.abbrv
import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node

@Node
data class Author(
    @Id
    @GeneratedValue(GenerateCode::class)
    val id: String? = null,
    val name: String? = null,
) {
    @JsonIgnore
    fun getGeneratedId(): String {
        return name?.abbrv().toString()
    }
}
