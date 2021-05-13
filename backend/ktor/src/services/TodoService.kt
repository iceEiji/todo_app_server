package com.todo.example.services

import com.todo.example.dao.TodosTable
import com.todo.example.factories.DatabaseFactory.dbQuery
import com.todo.example.models.NewTodo
import com.todo.example.models.Todo
import org.jetbrains.exposed.sql.*

class TodoService {
    suspend fun getAll(): List<Todo> = dbQuery {
        TodosTable.selectAll().map {
            it.toTodo()
        }
    }

    suspend fun get(id: Int): Todo? = dbQuery {
        TodosTable.select {
            TodosTable.id eq id
        }.mapNotNull {
            it.toTodo()
        }.singleOrNull()
    }

    suspend fun add(todo: NewTodo): Todo {
        var key = 0
        dbQuery {
            key = (TodosTable.insert {
                it[task] = todo.task
            } get TodosTable.id).value
        }
        return get(key)!!
    }

    suspend fun update(todo: NewTodo): Todo? {
        val id = todo.id
        return if (id == null) {
            add(todo)
        } else {
            dbQuery {
                TodosTable.update({ TodosTable.id eq id }) {
                    it[task] = todo.task
                }
            }
            get(id)
        }
    }

    suspend fun delete(id: Int): Boolean {
        val removedCount = dbQuery {
            TodosTable.deleteWhere { TodosTable.id eq id }
        }
        return removedCount > 0
    }

    private fun ResultRow.toTodo() = Todo(id = get(TodosTable.id).value, task = get(TodosTable.task))
}