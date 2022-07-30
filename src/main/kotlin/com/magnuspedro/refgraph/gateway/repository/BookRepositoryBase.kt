package com.magnuspedro.refgraph.gateway.repository

import com.magnuspedro.refgraph.entities.vertices.Book
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository
import java.util.UUID


interface BookRepositoryBase : ReactiveNeo4jRepository<Book, UUID> {
}