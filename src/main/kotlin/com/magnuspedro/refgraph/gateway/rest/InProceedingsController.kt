package com.magnuspedro.refgraph.gateway.rest

import com.magnuspedro.refgraph.entities.requests.CreateAndRelateArticle
import com.magnuspedro.refgraph.entities.vertices.Article
import com.magnuspedro.refgraph.entities.vertices.InProceedings
import com.magnuspedro.refgraph.gateway.repository.InProceedingsRepository
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1/refgraph")
class InProceedingsController(
    val inProceedingsRepository: InProceedingsRepository
) {

    @PostMapping("/inproceedins")
    fun createArticle(@RequestBody inProceedings: InProceedings): Mono<InProceedings> {
        return this.inProceedingsRepository.save(inProceedings)
    }

    @PostMapping("/inproceedins/citation")
    fun createArticleCitation(@RequestBody createAndRelateArticle: CreateAndRelateArticle): Mono<MutableList<InProceedings>> {
        return this.inProceedingsRepository.createRelation(createAndRelateArticle)
    }
}