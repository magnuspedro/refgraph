package com.magnuspedro.refgraph.gateway.repository

import com.magnuspedro.refgraph.entities.vertices.InProceedings
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository
import java.util.UUID


interface InProceedingsRepository : ReactiveNeo4jRepository<InProceedings, UUID> {
}