package com.magnuspedro.refgraph.config

import org.apache.commons.text.WordUtils
import org.springframework.data.neo4j.core.schema.IdGenerator
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
            .get(entity)
        return WordUtils.initials(value as String?).uppercase()
    }
}