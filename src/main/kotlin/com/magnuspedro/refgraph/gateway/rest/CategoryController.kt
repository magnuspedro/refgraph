package com.magnuspedro.refgraph.gateway.rest

import com.magnuspedro.refgraph.entities.requests.CategoryRequest
import com.magnuspedro.refgraph.entities.vertices.Category
import com.magnuspedro.refgraph.gateway.repository.CategoryRepositoryCustom
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import jakarta.validation.Valid

@RestController
@RequestMapping("/api/v1/refgraph/category")
class CategoryController(
    private val categoryRepository: CategoryRepositoryCustom
) {

    @PostMapping
    @Operation(summary = "Create category", security = [SecurityRequirement(name = "bearerAuth")])
    fun createArticle(@RequestBody @Valid categoryRequest: CategoryRequest): Mono<Category> {
        val category = Category(
            name = categoryRequest.name,
        )
        return this.categoryRepository.save(category)
    }

    @GetMapping
    @Operation(summary = "Find all categories", security = [SecurityRequirement(name = "bearerAuth")])
    fun findAllCategories(): Flux<Category> {
        return this.categoryRepository.findAll()
    }
}