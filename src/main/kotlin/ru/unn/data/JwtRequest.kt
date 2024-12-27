@kotlinx.serialization.Serializable
data class JwtRequest(
    val email: String,
    val password: String
)