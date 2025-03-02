package com.sandbox.testapp.seeder

import com.sandbox.testapp.model.Todo
import com.sandbox.testapp.repository.TodoRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class TodoDBSeeder(
    private val todoRepository: TodoRepository,
) : CommandLineRunner {

    private val logger: Logger = LoggerFactory.getLogger(TodoDBSeeder::class.java)

    override fun run(vararg args: String?) {
        if (todoRepository.count() == 0L) {
            val todos = listOf(
                Todo(
                    title = "Buy groceries",
                    description = "Milk, eggs, bread",
                    completed = false,
                    createdAt = LocalDateTime.now().minusDays(2),
                    updatedAt = LocalDateTime.now().minusDays(2)
                ),
                Todo(
                    title = "Write report",
                    description = "Q4 financial report",
                    completed = true,
                    createdAt = LocalDateTime.now().minusHours(5),
                    updatedAt = LocalDateTime.now().minusHours(3)
                )
            )

            logger.info("Saving ${todos.size} todos")
            todoRepository.saveAll(todos)
        } else {
            logger.info("Database already seeded")
        }
    }
}