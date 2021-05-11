package com.todo.example.models

data class Todo(
    val id: Int,
    val task: String
)

data class NewTodo(
    val id: Int?,
    val task: String
)