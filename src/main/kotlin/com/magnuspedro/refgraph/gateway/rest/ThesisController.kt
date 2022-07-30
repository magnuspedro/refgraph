package com.magnuspedro.refgraph.gateway.rest

import com.magnuspedro.refgraph.entities.requests.ThesisRequest
import com.magnuspedro.refgraph.entities.vertices.Thesis
import com.magnuspedro.refgraph.gateway.repository.ThesisRepository
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1/refgraph/thesis")
class ThesisController(
    val thesisRepository: ThesisRepository
) {

    @PostMapping
    @Operation(summary = "Create thesis", security = [SecurityRequirement(name = "bearerAuth")])
    fun createThesis(@RequestBody thesisRequest: ThesisRequest): Mono<Thesis> {
        val thesis = Thesis(
            title = thesisRequest.title,
            date = thesisRequest.date,
            school = thesisRequest.school,
            code = thesisRequest.code
        )
        return this.thesisRepository.save(thesis)
    }

    @GetMapping
    @Operation(summary = "Find all thesis", security = [SecurityRequirement(name = "bearerAuth")])
    fun findAllThesis(): Flux<Thesis> {
        return this.thesisRepository.findAll()
    }
}