package com.todo.example.dao

import org.jetbrains.exposed.dao.IntIdTable
import org.jetbrains.exposed.sql.Column

object AccountsTable : IntIdTable() {
    val password: Column<String> = varchar("password", 4000)
    val name: Column<String> = varchar("name", 4000)
    val email: Column<String> = varchar("email", 4000)
}