package com.todo.example.services

import com.todo.example.dao.AccountsTable
import com.todo.example.factories.DatabaseFactory.dbQuery
import com.todo.example.models.Account
import com.todo.example.models.NewAccount
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import java.security.MessageDigest

class AccountService {
    private suspend fun getAccount(id: Int): Account? = dbQuery {
        AccountsTable.select {
            (AccountsTable.id eq id)
        }.mapNotNull {
            it.toAccount()
        }.singleOrNull()
    }

    suspend fun createAccount(account: NewAccount): Account {
        var key = 0
        dbQuery {
            key = (AccountsTable.insert {
                it[password] = createHash(account.password)
                it[name] = account.name
                it[email] = account.email
            } get AccountsTable.id).value
        }
        return getAccount(key)!!
    }

    suspend fun authentication(email: String, password: String): Account? = dbQuery {
        AccountsTable.select {
            (AccountsTable.email eq email) and (AccountsTable.password eq createHash(password))
        }.mapNotNull {
            it.toAccount()
        }.singleOrNull()
    }

    private fun createHash(value: String): String {
        return MessageDigest.getInstance("SHA-256")
            .digest(value.toByteArray())
            .joinToString(separator = "") {
                "%02x".format(it)
            }
    }

    private fun ResultRow.toAccount() = Account(
        id = get(AccountsTable.id).value,
        password = get(AccountsTable.password),
        name = get(AccountsTable.name),
        email = get(AccountsTable.email)
    )
}
