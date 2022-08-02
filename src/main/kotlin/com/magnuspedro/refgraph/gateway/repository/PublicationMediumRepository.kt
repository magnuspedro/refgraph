package com.magnuspedro.refgraph.gateway.repository

import com.magnuspedro.refgraph.entities.vertices.PublicationMedium
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository
import java.util.*

interface PublicationMediumRepository : ReactiveNeo4jRepository<PublicationMedium, UUID>,
    PublicationMediumRepositoryCustom