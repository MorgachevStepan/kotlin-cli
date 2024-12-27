package ru.unn

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.client.request.*
import kotlinx.serialization.json.Json

object HttpClientFactory {
    private var accessToken: String? = null

    fun setAccessToken(token: String?) {
        accessToken = token
    }

    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
        defaultRequest {
            accessToken?.let {
                header("Authorization", "Bearer $it")
            }
            header("Content-Type", "application/json")
        }
    }
}
