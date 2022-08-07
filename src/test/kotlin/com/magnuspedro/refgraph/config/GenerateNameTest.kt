package com.magnuspedro.refgraph.config

import com.magnuspedro.refgraph.entities.vertices.Category
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.web.server.ResponseStatusException

internal class GenerateNameTest {
    private lateinit var generateName: GenerateName

    @BeforeEach
    fun setup() {
        generateName = GenerateName()
    }

    @Test
    fun `Should return name as id`() {
        val category = Category(name = "Name")
        val result = generateName.generateId("", category)

        assertEquals(category.name, result)
    }

    @Test
    fun `Should return error when name is null`() {
        val category = Category()

        org.junit.jupiter.api.assertThrows<ResponseStatusException> { generateName.generateId("", category) }
    }
}
