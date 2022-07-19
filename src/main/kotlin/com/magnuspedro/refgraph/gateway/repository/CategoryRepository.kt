package com.magnuspedro.refgraph.gateway.repository

import com.magnuspedro.refgraph.entities.vertices.Category
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository
import java.util.UUID

interface CategoryRepository : ReactiveNeo4jRepository<Category, UUID> {
}