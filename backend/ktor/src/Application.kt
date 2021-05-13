package com.todo.example

import com.fasterxml.jackson.databind.SerializationFeature
import com.todo.example.api.todos
import com.todo.example.factories.DatabaseFactory
import com.todo.example.services.TodoService
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.jackson.*
import io.ktor.routing.*

fun main(args: Array<String>): Unit = io.ktor.server.tomcat.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    install(ContentNegotiation) {
        jackson {
            configure(SerializationFeature.INDENT_OUTPUT, true)
        }
    }

    DatabaseFactory.init()

    val todoService = TodoService()
    install(Routing) {
        todos(todoService)
    }
}
