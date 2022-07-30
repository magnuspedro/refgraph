package com.magnuspedro.refgraph.gateway.rest

import com.magnuspedro.refgraph.entities.vertices.Category
import com.magnuspedro.refgraph.gateway.repository.CategoryRepository
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1/refgraph//category")
class CategoryController(
    val categoryRepository: CategoryRepository
) {

    @PostMapping
    @Operation(summary = "Create category", security = [SecurityRequirement(name = "bearerAuth")])
    fun createArticle(@RequestBody category: Category): Mono<Category> {
        return this.categoryRepository.save(category)
    }

    @GetMapping
    @Operation(summary = "Find all categories", security = [SecurityRequirement(name = "bearerAuth")])
    fun findAllCategories(): Flux<Category> {
        return this.categoryRepository.findAll()
    }
}