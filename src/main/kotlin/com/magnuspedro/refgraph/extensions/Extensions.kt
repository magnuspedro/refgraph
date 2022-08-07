package com.magnuspedro.refgraph.extensions

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono
import kotlin.math.log

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
            return this.split(" ").joinToString("") {
                it.slice(IntRange(0, log(it.length.toDouble(), 3.0).toInt()))
            }.uppercase()
        }
    }
}