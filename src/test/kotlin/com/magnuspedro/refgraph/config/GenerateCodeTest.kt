package com.magnuspedro.refgraph.config

import com.magnuspedro.refgraph.entities.vertices.Category
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.web.server.ResponseStatusException

internal class GenerateCodeTest {
    private lateinit var generateCode: GenerateCode

    @BeforeEach
    fun setup() {
        generateCode = GenerateCode()
    }

    @Test
    fun `Should abbreviate word`() {
        val category = Category(name = "Name")
        val result = generateCode.generateId("", category)

        assertEquals("NA", result)
    }

    @Test
    fun `Should return error when name is null`() {
        val category = Category()

        assertThrows<ResponseStatusException> { generateCode.generateId("", category) }
    }

}