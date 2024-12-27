@kotlinx.serialization.Serializable
data class JwtResponse(
    val email: String,
    val accessToken: String,
    val refreshToken: String
)