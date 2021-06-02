package com.todo.example

import com.fasterxml.jackson.databind.SerializationFeature
import com.todo.example.api.todos
import com.todo.example.factories.DatabaseFactory
import com.todo.example.models.AuthUser
import com.todo.example.services.TodoService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.jackson.*
import io.ktor.routing.*

fun main(args: Array<String>): Unit = io.ktor.server.tomcat.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    // クライアントのリクエストをログ出力
    install(CallLogging)

    // Content-TypeとAcceptヘッダーに応じて自動でのコンテンツの変換を行う
    install(ContentNegotiation) {
        jackson {
            configure(SerializationFeature.INDENT_OUTPUT, true)
        }
    }

    // 認証を行う
    install(Authentication) {
        val jwtConfig = JWTConfig()
        jwt {
            realm = jwtConfig.realm
            verifier(jwtConfig.makeJwtVerifier())
            validate { jwtCredential ->
                if (jwtCredential.payload.audience.contains(jwtConfig.audience)) {
                     val principal = JWTPrincipal(jwtCredential.payload)
                    AuthUser.toAuthUser(principal)
                    principal
                } else null
            }
        }
    }

    // DB接続、マイグレーション
    DatabaseFactory.init()

    // ルーティング
    val todoService = TodoService()
    install(Routing) {
        todos(todoService)
    }
}
