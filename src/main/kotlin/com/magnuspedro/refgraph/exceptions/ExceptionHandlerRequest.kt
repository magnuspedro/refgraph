package com.magnuspedro.refgraph.exceptions

import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.bind.support.WebExchangeBindException
import org.springframework.web.server.ServerWebInputException

@RestControllerAdvice
class ExceptionHandlerRequest {
    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(WebExchangeBindException::class)
    @ResponseBody
    fun validationError(ex: WebExchangeBindException): ResponseEntity<List<ErrorMessage>> {
        val fieldErrors = ex.bindingResult.fieldErrors
        val message = fieldErrors.map { ErrorMessage(field = it.field, message = it.defaultMessage) }

        log.error("Error validating fields $message")
        return ResponseEntity.badRequest().body(message)
    }

    @ExceptionHandler(ServerWebInputException::class)
    @ResponseBody
    fun onException(exception: ServerWebInputException): ResponseEntity<List<ErrorMessage>> {
        val path = (exception.rootCause as MissingKotlinParameterException).path
        val message = path.map { ErrorMessage(field = it.fieldName, message = "must not be null") }

        return ResponseEntity.badRequest().body(message)
    }
}

data class ErrorMessage(
    val field: String,
    val message: String?
)