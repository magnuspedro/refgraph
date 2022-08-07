package com.magnuspedro.refgraph.config

import com.magnuspedro.refgraph.extensions.Extensions.Companion.abbrv
import org.springframework.data.neo4j.core.schema.IdGenerator
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

class GenerateCode : IdGenerator<String> {
    companion object {
        private const val FIELD_NAME = "name"
    }

    override fun generateId(primaryLabel: String, entity: Any): String {
        val value = entity.javaClass.kotlin.memberProperties
            .single { it.name == FIELD_NAME }
            .also { it.isAccessible = true }
            .get(entity) as String?
        return value?.abbrv() ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "name cannot be empty")
    }
}