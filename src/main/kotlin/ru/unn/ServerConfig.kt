package ru.unn

import java.io.File

object ServerConfig {
    private const val CONFIG_FILE = ".server_config"

    var serverUrl: String
        get() {
            val file = File(CONFIG_FILE)
            return if (file.exists()) file.readText().trim() else "http://localhost:8080"
        }
        set(value) {
            File(CONFIG_FILE).writeText(value)
        }

    fun resetToDefault() {
        serverUrl = "http://localhost:8080"
    }

}
