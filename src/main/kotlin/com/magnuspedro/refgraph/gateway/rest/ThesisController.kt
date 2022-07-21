package com.magnuspedro.refgraph.gateway.rest

import com.magnuspedro.refgraph.entities.requests.CreateAndRelateArticle
import com.magnuspedro.refgraph.entities.vertices.Article
import com.magnuspedro.refgraph.entities.vertices.InProceedings
import com.magnuspedro.refgraph.entities.vertices.Thesis
import com.magnuspedro.refgraph.gateway.repository.ThesisRepository
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1/refgraph/thesis")
class ThesisController(
    val thesisRepository: ThesisRepository
) {

    @PostMapping
    fun createThesis(@RequestBody thesis: Thesis): Mono<Thesis> {
        return this.thesisRepository.save(thesis)
    }

    @PostMapping("/citation")
    fun createThesisCitation(@RequestBody createAndRelateArticle: CreateAndRelateArticle): Mono<MutableList<Thesis>> {
        return this.thesisRepository.createRelation(createAndRelateArticle)
    }

    @GetMapping
    fun findAllThesis(): Flux<Thesis> {
        return this.thesisRepository.findAll()
    }
}