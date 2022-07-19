package com.magnuspedro.refgraph.gateway.rest

import com.magnuspedro.refgraph.entities.requests.CreateAndRelateArticle
import com.magnuspedro.refgraph.entities.vertices.Book
import com.magnuspedro.refgraph.gateway.repository.BookRepositoryBase
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1/refgraph")
class BookController(
    val bookRepository: BookRepositoryBase
) {


    @PostMapping("/book")
    fun createArticle(@RequestBody book: Book): Mono<Book> {
        return this.bookRepository.save(book)
    }

    @PostMapping("/book/citation")
    fun createArticleCitation(@RequestBody createAndRelateArticle: CreateAndRelateArticle): Mono<MutableList<Book>> {
        return this.bookRepository.createRelation(createAndRelateArticle)
    }
}