package com.sandbox.testapp.service

import com.sandbox.testapp.model.Todo
import com.sandbox.testapp.repository.TodoRepository
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDateTime
import java.util.Optional
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@ExtendWith(MockitoExtension::class)
class TodoServiceTest {
    @Mock
    lateinit var todoRepository: TodoRepository
    @InjectMocks
    lateinit var todoService: TodoService

    @Test
    fun testCreate() {
        // Arrange
        val todo = Todo(
            id = 1,
            title = "test",
            description = "test",
        )
        Mockito.`when`(todoRepository.save(todo)).thenReturn(todo)
        // Act
        val result = todoService.createTodo(todo)
        // Assert
        Mockito.verify(todoRepository, Mockito.times(1)).save(todo)
        assertNotNull(result.createdAt)
        assertNotNull(result.updatedAt)
    }

    @Test
    fun testGetAllTodos() {
        // Arrange
        val todoList = listOf(
            Todo(id = 1, title = "Todo 1", description = "Description 1"),
            Todo(id = 2, title = "Todo 2", description = "Description 2")
        )
        Mockito.`when`(todoRepository.findAll()).thenReturn(todoList)

        // Act
        val result = todoService.getAllTodos()

        // Assert
        assertEquals(2, result.size)
        assertEquals(todoList, result)
        Mockito.verify(todoRepository, Mockito.times(1)).findAll()
    }

    @Test
    fun testGetTodosByStatus() {
        // Arrange
        val completedTodos = listOf(
            Todo(id = 1, title = "Todo 1", description = "Description 1", completed = true),
            Todo(id = 2, title = "Todo 2", description = "Description 2", completed = true)
        )
        Mockito.`when`(todoRepository.findByCompleted(true)).thenReturn(completedTodos)

        // Act
        val result = todoService.getTodosByStatus(true)

        // Assert
        assertEquals(2, result.size)
        assertTrue(result.all { it.completed })
        Mockito.verify(todoRepository, Mockito.times(1)).findByCompleted(true)
    }

    @Test
    fun testSearchTodosByTitle() {
        // Arrange
        val searchTerm = "test"
        val matchingTodos = listOf(
            Todo(id = 1, title = "test todo", description = "Description 1"),
            Todo(id = 2, title = "another test", description = "Description 2")
        )
        Mockito.`when`(todoRepository.findByTitleContainingIgnoreCase(searchTerm)).thenReturn(matchingTodos)

        // Act
        val result = todoService.searchTodosByTitle(searchTerm)

        // Assert
        assertEquals(2, result.size)
        assertTrue(result.all { it.title.contains(searchTerm, ignoreCase = true) })
        Mockito.verify(todoRepository, Mockito.times(1)).findByTitleContainingIgnoreCase(searchTerm)
    }

    @Test
    fun testGetTodoById_ExistingId() {
        // Arrange
        val todoId = 1L
        val todo = Todo(id = todoId, title = "Test Todo", description = "Description")
        Mockito.`when`(todoRepository.findById(todoId)).thenReturn(Optional.of(todo))

        // Act
        val result = todoService.getTodoById(todoId)

        // Assert
        assertTrue(result.isPresent)
        assertEquals(todoId, result.get().id)
        Mockito.verify(todoRepository, Mockito.times(1)).findById(todoId)
    }

    @Test
    fun testGetTodoById_NonExistingId() {
        // Arrange
        val todoId = 999L
        Mockito.`when`(todoRepository.findById(todoId)).thenReturn(Optional.empty())

        // Act
        val result = todoService.getTodoById(todoId)

        // Assert
        assertFalse(result.isPresent)
        Mockito.verify(todoRepository, Mockito.times(1)).findById(todoId)
    }

    @Test
    fun testUpdateTodo_ExistingId() {
        // Arrange
        val todoId = 1L
        val existingTodo = Todo(
            id = todoId,
            title = "Original Title",
            description = "Original Description",
            completed = false
        )

        val updatedDetails = Todo(
            id = todoId,
            title = "Updated Title",
            description = "Updated Description",
            completed = true
        )

        val savedTodo = Todo(
            id = todoId,
            title = "Updated Title",
            description = "Updated Description",
            completed = true
        )
        savedTodo.updatedAt = LocalDateTime.now()

        Mockito.`when`(todoRepository.findById(todoId)).thenReturn(Optional.of(existingTodo))
        Mockito.`when`(todoRepository.save(any(Todo::class.java))).thenReturn(savedTodo)

        // Act
        val result = todoService.updateTodo(todoId, updatedDetails)

        // Assert
        assertEquals("Updated Title", result.title)
        assertEquals("Updated Description", result.description)
        assertTrue(result.completed)
        assertNotNull(result.updatedAt)
        Mockito.verify(todoRepository, Mockito.times(1)).findById(todoId)
        Mockito.verify(todoRepository, Mockito.times(1)).save(any(Todo::class.java))
    }

    @Test
    fun testUpdateTodo_NonExistingId() {
        // Arrange
        val todoId = 999L
        val updatedDetails = Todo(
            id = todoId,
            title = "Updated Title",
            description = "Updated Description",
            completed = true
        )

        Mockito.`when`(todoRepository.findById(todoId)).thenReturn(Optional.empty())

        // Act & Assert
        assertThrows<RuntimeException> {
            todoService.updateTodo(todoId, updatedDetails)
        }

        Mockito.verify(todoRepository, Mockito.times(1)).findById(todoId)
        Mockito.verify(todoRepository, Mockito.never()).save(any(Todo::class.java))
    }

    @Test
    fun testMarkTodoAsCompleted_ExistingId() {
        // Arrange
        val todoId = 1L
        val todo = Todo(
            id = todoId,
            title = "Test Todo",
            description = "Description",
            completed = false
        )

        val completedTodo = Todo(
            id = todoId,
            title = "Test Todo",
            description = "Description",
            completed = true
        )
        completedTodo.updatedAt = LocalDateTime.now()

        Mockito.`when`(todoRepository.findById(todoId)).thenReturn(Optional.of(todo))
        Mockito.`when`(todoRepository.save(any(Todo::class.java))).thenReturn(completedTodo)

        // Act
        val result = todoService.markTodoAsCompleted(todoId)

        // Assert
        assertTrue(result.completed)
        assertNotNull(result.updatedAt)
        Mockito.verify(todoRepository, Mockito.times(1)).findById(todoId)
        Mockito.verify(todoRepository, Mockito.times(1)).save(any(Todo::class.java))
    }

    @Test
    fun testMarkTodoAsCompleted_NonExistingId() {
        // Arrange
        val todoId = 999L
        Mockito.`when`(todoRepository.findById(todoId)).thenReturn(Optional.empty())

        // Act & Assert
        assertThrows<RuntimeException> {
            todoService.markTodoAsCompleted(todoId)
        }

        Mockito.verify(todoRepository, Mockito.times(1)).findById(todoId)
        Mockito.verify(todoRepository, Mockito.never()).save(any(Todo::class.java))
    }

    @Test
    fun testDeleteTodo_ExistingId() {
        // Arrange
        val todoId = 1L
        val todo = Todo(
            id = todoId,
            title = "Test Todo",
            description = "Description"
        )

        Mockito.`when`(todoRepository.findById(todoId)).thenReturn(Optional.of(todo))
        Mockito.doNothing().`when`(todoRepository).delete(todo)

        // Act
        todoService.deleteTodo(todoId)

        // Assert
        Mockito.verify(todoRepository, Mockito.times(1)).findById(todoId)
        Mockito.verify(todoRepository, Mockito.times(1)).delete(todo)
    }

    @Test
    fun testDeleteTodo_NonExistingId() {
        // Arrange
        val todoId = 999L
        Mockito.`when`(todoRepository.findById(todoId)).thenReturn(Optional.empty())

        // Act & Assert
        (assertThrows<RuntimeException> {
            todoService.deleteTodo(todoId)
        })

        Mockito.verify(todoRepository, Mockito.times(1)).findById(todoId)
        Mockito.verify(todoRepository, Mockito.never()).delete(any(Todo::class.java))
    }

    // Helper method for matching any Todo object
    private fun <T> any(type: Class<T>): T = Mockito.any(type)
}