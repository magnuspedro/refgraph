package com.magnuspedro.refgraph.gateway.repository

import com.magnuspedro.refgraph.entities.vertices.Author
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository
import java.util.*

interface AuthorRepository : ReactiveNeo4jRepository<Author, UUID>, AuthorRepositoryCustom