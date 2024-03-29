package com.magnuspedro.refgraph.gateway.repository

import com.magnuspedro.refgraph.entities.vertices.Article
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository


interface ArticleRepository : ReactiveNeo4jRepository<Article, String>