package com.todo.example.api

import com.todo.example.models.NewTodo
import com.todo.example.services.TodoService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.todos(todoService: TodoService) {
    route("todos") {
        get("/") {
            call.respond(todoService.getAll())
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw IllegalStateException("Must To id")
            val todo = todoService.get(id)
            if (todo == null) call.respond(HttpStatusCode.NotFound)
            else call.respond(todo)
        }

        post("/") {
            val newTodo = call.receive<NewTodo>()
            call.respond(HttpStatusCode.Created, todoService.add(newTodo))
        }

        put("/{id}") {
            val todo = call.receive<NewTodo>()
            val updated = todoService.update(todo)
            if (updated == null) call.respond(HttpStatusCode.NotFound)
            else call.respond(HttpStatusCode.OK, updated)
        }

        delete("/{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw IllegalStateException("Must To id")
            val removed = todoService.delete(id)
            if (removed) call.respond(HttpStatusCode.OK)
            else call.respond(HttpStatusCode.NotFound)
        }
    }
}
