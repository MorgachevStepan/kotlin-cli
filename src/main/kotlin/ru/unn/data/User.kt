import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    @SerialName("users") val users: List<User>
)

@Serializable
data class User(
    val id: Int,
    val email: String,
    val role: String
)
