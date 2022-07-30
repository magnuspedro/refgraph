package com.magnuspedro.refgraph.gateway.rest

import com.magnuspedro.refgraph.entities.requests.InProceedingsRequest
import com.magnuspedro.refgraph.entities.vertices.InProceedings
import com.magnuspedro.refgraph.gateway.repository.InProceedingsRepository
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1/refgraph/inproceedings")
class InProceedingsController(
    val inProceedingsRepository: InProceedingsRepository
) {

    @PostMapping
    @Operation(summary = "Create inproceedings", security = [SecurityRequirement(name = "bearerAuth")])
    fun createInProceedings(@RequestBody inProceedingsRequest: InProceedingsRequest): Mono<InProceedings> {
        val inProceedings = InProceedings(
            title = inProceedingsRequest.title,
            bookTitle = inProceedingsRequest.bookTitle,
            date = inProceedingsRequest.date,
            code = inProceedingsRequest.code
        )
        return this.inProceedingsRepository.save(inProceedings)
    }

    @GetMapping
    @Operation(summary = "Find all inproceedings", security = [SecurityRequirement(name = "bearerAuth")])
    fun findAllInProceedings(): Flux<InProceedings> {
        return this.inProceedingsRepository.findAll()
    }
}