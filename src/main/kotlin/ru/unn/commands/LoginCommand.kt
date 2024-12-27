package ru.unn.commands

import JwtRequest
import JwtResponse
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.prompt
import io.ktor.client.statement.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import ru.unn.HttpClientFactory
import ru.unn.ServerConfig
import ru.unn.TokenStorage

class LoginCommand : BaseCommand(
    help = "Login as admin.\n\n" +
            "Use this command to authenticate and retrieve an access token."
) {
    private val email by option(
        "--email",
        help = "Email address of the admin user."
    ).prompt("Email")

    private val password by option(
        "--password",
        help = "Password for the admin user."
    ).prompt("Password")

    override fun run() {
        runBlocking {
            try {
                val response: HttpResponse = HttpClientFactory.client.post("${ServerConfig.serverUrl}/api/v1/auth/login") {
                    setBody(JwtRequest(email, password))
                }

                if (response.status == HttpStatusCode.OK) {
                    val jwtResponse: JwtResponse = Json.decodeFromString(response.bodyAsText())
                    println("Login successful.\nAccess Token: ${jwtResponse.accessToken}")
                    HttpClientFactory.setAccessToken(jwtResponse.accessToken)
                    TokenStorage.saveToken(jwtResponse.accessToken)
                } else {
                    handleErrorResponse(response)
                }
            } catch (e: Exception) {
                println("Unexpected error: ${e.message}")
            }
        }
    }

}
