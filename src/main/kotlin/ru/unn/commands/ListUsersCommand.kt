package ru.unn.commands

import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import ru.unn.HttpClientFactory
import UserResponse
import io.ktor.http.*
import ru.unn.ServerConfig

class ListUsersCommand : BaseCommand(
    help = "Retrieve and display all registered users.\n\n" +
            "This command fetches a list of users from the server and displays " +
            "their ID, email, and role in a tabular format."
) {
    override fun run() {
        runBlocking {
            try {
                val response: HttpResponse = HttpClientFactory.client.get("${ServerConfig.serverUrl}/api/v1/admin/users")

                if (response.status == HttpStatusCode.OK) {
                    val usersResponse: UserResponse = Json.decodeFromString(response.bodyAsText())
                    println(String.format("%-5s %-30s %-15s", "ID", "Email", "Role"))
                    println("-".repeat(50))

                    usersResponse.users.forEach { user ->
                        println(String.format("%-5d %-30s %-15s", user.id, user.email, user.role))
                    }
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
