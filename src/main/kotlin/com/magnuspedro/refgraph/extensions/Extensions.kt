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

        fun String.abbrv(): String {
            return this.split(" ").joinToString("-") {
                if (it.length == 1) it.slice(IntRange(0, 0)) else it.slice(IntRange(0, 1))
            }.uppercase()
        }
    }
}