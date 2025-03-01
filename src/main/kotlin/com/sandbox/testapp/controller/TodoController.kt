package com.sandbox.testapp.controller

import com.sandbox.testapp.model.Todo
import com.sandbox.testapp.service.TodoService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/todos")
class TodoController(private val todoService: TodoService) {

    @PostMapping
    fun createTodo(@RequestBody todo: Todo): ResponseEntity<Todo> {
        return ResponseEntity(todoService.createTodo(todo), HttpStatus.CREATED)
    }

    @GetMapping
    fun getAllTodos(): ResponseEntity<List<Todo>> {
        return ResponseEntity(todoService.getAllTodos(), HttpStatus.OK)
    }

    @GetMapping("/{id}")
    fun getTodoById(@PathVariable id: Long): ResponseEntity<Todo> {
        val todo = todoService.getTodoById(id)
        return if (todo.isPresent) {
            ResponseEntity(todo.get(), HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("/status")
    fun getTodosByStatus(@RequestParam completed: Boolean): ResponseEntity<List<Todo>> {
        return ResponseEntity(todoService.getTodosByStatus(completed), HttpStatus.OK)
    }

    @GetMapping("/search")
    fun searchTodos(@RequestParam title: String): ResponseEntity<List<Todo>> {
        return ResponseEntity(todoService.searchTodosByTitle(title), HttpStatus.OK)
    }

    @PutMapping("/{id}")
    fun updateTodo(@PathVariable id: Long, @RequestBody todoDetails: Todo): ResponseEntity<Todo> {
        return try {
            val updatedTodo = todoService.updateTodo(id, todoDetails)
            ResponseEntity(updatedTodo, HttpStatus.OK)
        } catch (e: RuntimeException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PatchMapping("/{id}/complete")
    fun markTodoAsCompleted(@PathVariable id: Long): ResponseEntity<Todo> {
        return try {
            val completedTodo = todoService.markTodoAsCompleted(id)
            ResponseEntity(completedTodo, HttpStatus.OK)
        } catch (e: RuntimeException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @DeleteMapping("/{id}")
    fun deleteTodo(@PathVariable id: Long): ResponseEntity<Void> {
        return try {
            todoService.deleteTodo(id)
            ResponseEntity(HttpStatus.NO_CONTENT)
        } catch (e: RuntimeException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }
}