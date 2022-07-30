package com.magnuspedro.refgraph.gateway.rest

import com.magnuspedro.refgraph.entities.requests.BookRequest
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
    fun createBook(@RequestBody bookRequest: BookRequest): Mono<Book> {
        val book = Book(
            title = bookRequest.title,
            date = bookRequest.date,
            publisher = bookRequest.publisher,
            edition = bookRequest.edition,
            code = bookRequest.code
        )
        return this.bookRepository.save(book)
    }

    @GetMapping
    @Operation(summary = "Find all books", security = [SecurityRequirement(name = "bearerAuth")])
    fun findAllBooks(): Flux<Book> {
        return this.bookRepository.findAll()
    }
}