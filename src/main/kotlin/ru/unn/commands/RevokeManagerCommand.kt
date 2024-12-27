package ru.unn.commands

import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.prompt
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import ru.unn.HttpClientFactory
import ru.unn.ServerConfig

class RevokeManagerCommand : BaseCommand(
    help = "Revoke the manager role from a user by their ID.\n\n" +
            "This command sends a request to the server to revoke the role of manager " +
            "from the user with the specified ID."
) {
    private val userId by option(
        "--id",
        help = "The ID of the user to revoke the manager role."
    ).prompt("Enter the user ID")

    override fun run() {
        runBlocking {
            try {
                val response: HttpResponse = HttpClientFactory.client
                    .put("${ServerConfig.serverUrl}/api/v1/admin/users/$userId/revoke-manager")

                if (response.status == HttpStatusCode.OK) {
                    val responseText = response.bodyAsText()
                    handleResponse(responseText, "Successfully revoked manager role from user with ID: $userId.")
                } else {
                    handleErrorResponse(response)
                }
            } catch (e: ClientRequestException) {
                handleErrorResponse(e.response)
            } catch (e: ServerResponseException) {
                println("Server error occurred: ${e.message}")
            } catch (e: Exception) {
                println("Unexpected error: ${e.message}")
            }
        }
    }
}
