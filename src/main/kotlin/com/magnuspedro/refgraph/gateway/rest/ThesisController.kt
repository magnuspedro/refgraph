package com.magnuspedro.refgraph.gateway.rest

import com.magnuspedro.refgraph.entities.requests.CreateAndRelateArticle
import com.magnuspedro.refgraph.entities.vertices.Article
import com.magnuspedro.refgraph.entities.vertices.Thesis
import com.magnuspedro.refgraph.gateway.repository.ThesisRepository
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1/refgraph")
class ThesisController(
    val thesisRepository: ThesisRepository
) {

    @PostMapping("/thesis")
    fun createArticle(@RequestBody thesis: Thesis): Mono<Thesis> {
        return this.thesisRepository.save(thesis)
    }

    @PostMapping("/thesis/citation")
    fun createArticleCitation(@RequestBody createAndRelateArticle: CreateAndRelateArticle): Mono<MutableList<Thesis>> {
        return this.thesisRepository.createRelation(createAndRelateArticle)
    }
}