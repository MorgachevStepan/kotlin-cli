package ru.unn.commands

import com.github.ajalt.clikt.core.CliktCommand
import io.ktor.client.statement.*
import kotlinx.serialization.json.Json
import ru.unn.data.ErrorResponse

abstract class BaseCommand(
    help: String
) : CliktCommand(help = help) {

    protected fun handleResponse(responseText: String, successMessage: String) {
        if (responseText.trimStart().startsWith("{\"code\":")) {
            try {
                val errorResponse = Json.decodeFromString<ErrorResponse>(responseText)
                println("Error: ${errorResponse.message}")
            } catch (e: Exception) {
                println("Failed to parse error response: $responseText")
            }
        } else {
            println(successMessage)
            println("Server response: $responseText")
        }
    }

    protected suspend fun handleErrorResponse(response: HttpResponse) {
        try {
            val errorText = response.bodyAsText()
            var errorResponse = Json.decodeFromString<ErrorResponse>(errorText)
            if (errorResponse.message == "An Authentication object was not found in the SecurityContext") {
                errorResponse.message = "Ошибка аутентификации"
            }
            println("Error: ${errorResponse.message}")
        } catch (e: Exception) {
            println("Error: HTTP ${response.status.value} ${response.status.description}")
            println("Response body: ${response.bodyAsText()}")
        }
    }
}
