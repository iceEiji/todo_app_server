package com.todo.example

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.typesafe.config.ConfigFactory
import io.ktor.config.*
import io.ktor.util.*
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

@KtorExperimentalAPI
class JWTConfig {
    private val appConfig: ApplicationConfig

    init {
        appConfig = HoconApplicationConfig(ConfigFactory.load())
    }

    val algorithm: Algorithm
        get() {
            return Algorithm.HMAC256(appConfig.property("jwt.algorithm").getString())
        }

    val issuer: String
        get() {
            return appConfig.property("jwt.issuer").getString()
        }

    val audience: String
        get() {
            return appConfig.property("jwt.audience").getString()
        }

    val userId: String
        get() {
            return appConfig.property("jwt.userId").getString()
        }

    val realm: String
        get() {
            return appConfig.property("jwt.realm").getString()
        }

    fun makeJwtVerifier(): JWTVerifier = JWT
        .require(algorithm)
        .withAudience(audience)
        .withIssuer(issuer)
        .build()

    fun createToken(id: Int): String {
        return JWT.create()
            .withAudience(audience)
            .withExpiresAt(Date.from(LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.UTC))) // 有効期限
            .withClaim(userId, id)
            .withIssuer(issuer)
            .sign(algorithm)
    }

}