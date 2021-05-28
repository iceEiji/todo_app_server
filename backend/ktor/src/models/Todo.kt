package com.todo.example.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown=true)
data class Todo(
    val id: Int,
    val task: String
)

// NOTE: 未知のパラメータを無視しないとデシリアライズできずエラーになる。
@JsonIgnoreProperties(ignoreUnknown=true)
data class NewTodo(
    val id: Int?,
    val task: String
)