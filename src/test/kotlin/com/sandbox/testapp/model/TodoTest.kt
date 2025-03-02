package com.sandbox.testapp.model

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class TodoTest {

    @Test
    fun testCreateTodo() {
        val todo = Todo(
            id = 1,
            title = "test",
            description = "test",
        )

        assertNotNull(todo)

        assertNotNull(todo.createdAt)
        assertNotNull(todo.updatedAt)

        assertEquals(todo.title, "test")
        assertEquals(todo.description, "test")
    }
}