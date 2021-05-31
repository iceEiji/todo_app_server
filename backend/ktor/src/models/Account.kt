package com.todo.example.models

import io.ktor.auth.*
import io.ktor.auth.jwt.*

data class Account (
    val id: Int,
    val password: String,
    val name: String,
    val email: String
)

data class NewAccount (
    val id: Int?,
    val password: String,
    val name: String,
    val email: String
)

// TODO: ここに定義して適切か。
data class Login (
    val email: String,
    val password: String
)

// TODO: ここに定義して適切か。
data class AuthUser(val id: Int): Principal {
    companion object {
        fun toAuthUser(principal: JWTPrincipal) = AuthUser(
            principal.payload.getClaim("id")?.asInt()
                ?: throw IllegalStateException("unauthorized")
        )
    }
}