package com.magnuspedro.refgraph.gateway.rest

import com.magnuspedro.refgraph.entities.requests.CreateAndRelateArticle
import com.magnuspedro.refgraph.entities.vertices.Article
import com.magnuspedro.refgraph.entities.vertices.Category
import com.magnuspedro.refgraph.entities.vertices.InProceedings
import com.magnuspedro.refgraph.gateway.repository.InProceedingsRepository
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1/refgraph/inproceedings")
class InProceedingsController(
    val inProceedingsRepository: InProceedingsRepository
) {

    @PostMapping
    fun createInProceedings(@RequestBody inProceedings: InProceedings): Mono<InProceedings> {
        return this.inProceedingsRepository.save(inProceedings)
    }

    @PostMapping("/citation")
    fun createInProceedingsCitation(@RequestBody createAndRelateArticle: CreateAndRelateArticle): Mono<MutableList<InProceedings>> {
        return this.inProceedingsRepository.createRelation(createAndRelateArticle)
    }

    @GetMapping
    fun findAllInProceedings(): Flux<InProceedings> {
        return this.inProceedingsRepository.findAll()
    }
}