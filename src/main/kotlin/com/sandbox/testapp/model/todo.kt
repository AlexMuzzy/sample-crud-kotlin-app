package com.sandbox.testapp.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "todos")
class Todo(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "todo_generator")
    @SequenceGenerator(name = "todo_generator", sequenceName = "todos_seq", allocationSize = 1)
    var id: Long? = null,

    @Column(nullable = false)
    var title: String,

    @Column(nullable = false)
    var description: String,

    @Column(nullable = false)
    var completed: Boolean = false,

    @Column(updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()
)