package com.todo.example.dao

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable
import org.jetbrains.exposed.sql.Column

object TodosTable : IntIdTable() {
    val task: Column<String> = varchar("task", 4000)
}

class TodoEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<TodoEntity>(TodosTable)
    var task by TodosTable.task
}
