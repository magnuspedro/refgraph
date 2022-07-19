package com.magnuspedro.refgraph.gateway.repository

import com.magnuspedro.refgraph.entities.vertices.Thesis
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository
import java.util.UUID


interface ThesisRepository : ReactiveNeo4jRepository<Thesis, UUID>,  BaseArticleRepositoryCustom {
}