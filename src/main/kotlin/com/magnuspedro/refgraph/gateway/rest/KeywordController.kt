package com.magnuspedro.refgraph.gateway.rest

import com.magnuspedro.refgraph.entities.requests.KeywordRequest
import com.magnuspedro.refgraph.entities.vertices.Keyword
import com.magnuspedro.refgraph.gateway.repository.KeyWordRepositoryCustom
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import jakarta.validation.Valid

@RestController
@RequestMapping("/api/v1/refgraph/keyword")
class KeywordController(private val keyWordRepository: KeyWordRepositoryCustom) {

    @PostMapping
    @Operation(summary = "Create keyword", security = [SecurityRequirement(name = "bearerAuth")])
    fun createKeywords(@RequestBody @Valid keywordRequest: KeywordRequest): Mono<Keyword> {
        val keyword = Keyword(
            name = keywordRequest.name,
        )
        return this.keyWordRepository.save(keyword)
    }

    @GetMapping
    @Operation(summary = "Find all keywords", security = [SecurityRequirement(name = "bearerAuth")])
    fun findAllKeywords(): Flux<Keyword> {
        return this.keyWordRepository.findAll()
    }
}