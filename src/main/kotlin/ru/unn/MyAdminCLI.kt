package ru.unn

import com.github.ajalt.clikt.core.*

class MyAdminCLI : CliktCommand() {
    init {
        context { helpOptionNames = setOf("-h", "--help") }
    }

    override fun run() {
        val token = TokenStorage.loadToken()
        if (token != null) {
            HttpClientFactory.setAccessToken(token)
        }
    }
}
