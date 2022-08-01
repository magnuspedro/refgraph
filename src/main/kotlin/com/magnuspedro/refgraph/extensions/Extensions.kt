package com.magnuspedro.refgraph.extensions

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono

class Extensions {
    companion object {
        fun <T> verifyNull(mono: Mono<T>, message: String): Mono<T> {
            return mono.switchIfEmpty(
                Mono.error(
                    ResponseStatusException(
                        HttpStatus.UNPROCESSABLE_ENTITY, message
                    )
                )
            )

        }
    }
}