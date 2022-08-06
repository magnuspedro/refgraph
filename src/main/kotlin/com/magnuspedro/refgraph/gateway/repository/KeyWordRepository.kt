package com.magnuspedro.refgraph.gateway.repository

import com.magnuspedro.refgraph.entities.vertices.Keyword
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository

interface KeyWordRepository : ReactiveNeo4jRepository<Keyword, String>