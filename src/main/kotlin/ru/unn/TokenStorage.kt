package ru.unn

import java.io.File

object TokenStorage {
    private const val TOKEN_FILE = ".token"

    fun saveToken(token: String) {
        File(TOKEN_FILE).writeText(token)
    }

    fun loadToken(): String? {
        val file = File(TOKEN_FILE)
        return if (file.exists()) file.readText().trim() else null
    }

    fun removeToken(): Boolean {
        val file = File(TOKEN_FILE)
        return if (file.exists()) file.delete() else false
    }
}
