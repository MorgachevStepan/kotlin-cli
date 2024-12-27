package ru.unn.commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import ru.unn.ServerConfig

class SetServerCommand : CliktCommand(
    name = "set-server",
    help = "Set or view the server address for the CLI.\n\n" +
            "By default, the CLI connects to http://localhost:8080. Use this command to change or view the server address."
) {
    private val show by option(
        "--show",
        help = "Display the current server URL without making changes."
    ).flag(default = false)

    private val serverUrl by option(
        "--url",
        help = "The new server URL."
    )

    override fun run() {
        if (show) {
            println("Current server URL: ${ServerConfig.serverUrl}")
        } else if (serverUrl != null) {
            ServerConfig.serverUrl = serverUrl!!
            println("Server URL has been set to: ${ServerConfig.serverUrl}")
        } else {
            println("No action specified. Use --show to view the current URL or --url to set a new one.")
        }
    }
}
