package com.magnuspedro.refgraph.gateway.repository

import com.magnuspedro.refgraph.entities.vertices.Category
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Repository
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
class CategoryRepositoryCustom(private val repository: CategoryRepository) {
    fun save(category: Category): Mono<Category> {
        return repository.findById(category.getGeneratedId()).flatMap<Category?> {
            Mono.error(ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Category already exists"))
        }.switchIfEmpty(repository.save(category))
    }

    fun findAll(): Flux<Category> {
        return repository.findAll()
    }
}