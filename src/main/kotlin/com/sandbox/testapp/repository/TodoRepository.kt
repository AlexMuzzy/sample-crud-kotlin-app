package com.sandbox.testapp.repository

import com.sandbox.testapp.model.Todo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TodoRepository : JpaRepository<Todo, Long> {
    fun findByCompleted(completed: Boolean): List<Todo>
    fun findByTitleContainingIgnoreCase(title: String): List<Todo>
}