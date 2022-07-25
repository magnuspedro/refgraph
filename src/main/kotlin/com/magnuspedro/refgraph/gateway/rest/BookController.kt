package com.magnuspedro.refgraph.gateway.rest

import com.magnuspedro.refgraph.entities.requests.CreateAndRelateArticle
import com.magnuspedro.refgraph.entities.vertices.Author
import com.magnuspedro.refgraph.entities.vertices.Book
import com.magnuspedro.refgraph.gateway.repository.BookRepositoryBase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1/refgraph/book")
class BookController(
    val bookRepository: BookRepositoryBase
) {


    @PostMapping
    @Operation(summary = "Create books", security = [SecurityRequirement(name = "bearerAuth")])
    fun createBook(@RequestBody book: Book): Mono<Book> {
        return this.bookRepository.save(book)
    }

    @PostMapping("citation")
    @Operation(summary = "Create books with relations", security = [SecurityRequirement(name = "bearerAuth")])
    fun createBookCitation(@RequestBody createAndRelateArticle: CreateAndRelateArticle): Mono<MutableList<Book>> {
        return this.bookRepository.createRelation(createAndRelateArticle)
    }

    @GetMapping
    @Operation(summary = "Find all books", security = [SecurityRequirement(name = "bearerAuth")])
    fun findAllBooks(): Flux<Book> {
        return this.bookRepository.findAll()
    }
}