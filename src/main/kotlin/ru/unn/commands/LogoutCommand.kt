package ru.unn.commands

import com.github.ajalt.clikt.core.CliktCommand
import ru.unn.TokenStorage

class LogoutCommand : CliktCommand(
    name = "logout",
    help = "Logout the current user.\n\n" +
            "This command removes the stored access token and logs out the current user."
) {
    override fun run() {
        try {
            if (TokenStorage.removeToken()) {
                println("Successfully logged out.")
            } else {
                println("No active session found.")
            }
        } catch (e: Exception) {
            println("An error occurred while logging out: ${e.message}")
        }
    }
}
