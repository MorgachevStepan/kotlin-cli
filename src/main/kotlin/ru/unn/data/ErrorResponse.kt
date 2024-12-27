package ru.unn.data

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val code: String,
    var message: String
)
