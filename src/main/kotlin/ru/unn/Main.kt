package ru.unn

import com.github.ajalt.clikt.core.NoSuchSubcommand
import com.github.ajalt.clikt.core.PrintHelpMessage
import com.github.ajalt.clikt.core.subcommands
import ru.unn.commands.*

fun main(args: Array<String>) {
    try {
        MyAdminCLI()
            .subcommands(
                LoginCommand(),
                ListUsersCommand(),
                GrantManagerCommand(),
                RevokeManagerCommand(),
                LogoutCommand(),
                SetServerCommand()
            )
            .main(args)
    } catch (e: PrintHelpMessage) {
        println(e.command.getFormattedHelp())
    } catch (e: NoSuchSubcommand) {
        println("Error: No such command '${e.argument}'.\n")
        println(MyAdminCLI().getFormattedHelp())
    } catch (e: Exception) {
        println("Unexpected error: ${e.message}")
    }
}