package com.magnuspedro.refgraph.gateway.rest

import com.magnuspedro.refgraph.entities.vertices.Author
import com.magnuspedro.refgraph.entities.vertices.InProceedings
import com.magnuspedro.refgraph.gateway.repository.AuthorRepository
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1/refgraph")
class AuthorController(
    val authorRepository: AuthorRepository
) {

    @PostMapping("/author")
    fun createArticle(@RequestBody author: Author): Mono<Author> {
        return this.authorRepository.save(author)
    }
}