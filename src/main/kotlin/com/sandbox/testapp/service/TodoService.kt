package com.sandbox.testapp.service

import com.sandbox.testapp.model.Todo
import com.sandbox.testapp.repository.TodoRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.Optional

@Service
class TodoService(private val todoRepository: TodoRepository) {

    fun createTodo(todo: Todo): Todo = todo.apply {
        createdAt = LocalDateTime.now(); updatedAt = LocalDateTime.now()
    }.let { todo -> todoRepository.save(todo) }

    fun getAllTodos(): List<Todo> = todoRepository.findAll()

    fun getTodosByStatus(completed: Boolean): List<Todo> = todoRepository.findByCompleted(completed)

    fun searchTodosByTitle(title: String): List<Todo> = todoRepository.findByTitleContainingIgnoreCase(title)

    fun getTodoById(id: Long): Optional<Todo> = todoRepository.findById(id)

    fun updateTodo(id: Long, todoDetails: Todo): Todo {
        return todoRepository.findById(id).let { optional ->
            if (optional.isPresent) {
                val todo = optional.get()
                todo.title = todoDetails.title
                todo.description = todoDetails.description
                todo.completed = todoDetails.completed
                todo.updatedAt = LocalDateTime.now()
                todoRepository.save(todo)
            } else {
                throw RuntimeException("Todo not found with id: $id")
            }
        }
    }

    fun markTodoAsCompleted(id: Long): Todo {
        val todo = todoRepository.findById(id)
        return if (todo.isPresent) {
            todo.get().apply {
                completed = true
                updatedAt = LocalDateTime.now()
            }.let { todo -> todoRepository.save(todo) }
        } else {
            throw RuntimeException("Todo not found with id: $id")
        }
    }

    fun deleteTodo(id: Long) {
        val todo = todoRepository.findById(id)
            .orElseThrow { RuntimeException("Todo not found with id: $id") }

        todoRepository.delete(todo)
    }
}