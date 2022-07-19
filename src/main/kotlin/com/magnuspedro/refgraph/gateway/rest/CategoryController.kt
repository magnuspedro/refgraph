package com.magnuspedro.refgraph.gateway.rest

import com.magnuspedro.refgraph.entities.requests.CreateAndRelateArticle
import com.magnuspedro.refgraph.entities.vertices.Article
import com.magnuspedro.refgraph.entities.vertices.Category
import com.magnuspedro.refgraph.entities.vertices.Thesis
import com.magnuspedro.refgraph.gateway.repository.CategoryRepository
import com.magnuspedro.refgraph.gateway.repository.ThesisRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1/refgraph")
class CategoryController(
    val categoryRepository: CategoryRepository
) {

    @PostMapping("/category")
    fun createArticle(@RequestBody category: Category): Mono<Category> {
        return this.categoryRepository.save(category)
    }

    @GetMapping("/category")
    fun findAll(): Flux<Category> {
        return this.categoryRepository.findAll()
    }
}