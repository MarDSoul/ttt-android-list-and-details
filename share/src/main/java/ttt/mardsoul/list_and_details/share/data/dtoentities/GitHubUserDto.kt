package ttt.mardsoul.list_and_details.share.data.dtoentities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ttt.mardsoul.list_and_details.share.domain.entities.GitHubUser

@Serializable
data class GitHubUserDto(
	@SerialName("id")
	val id: Int,
	@SerialName("login")
	val login: String,
	@SerialName("avatar_url")
	val avatarUrl: String?,
	@SerialName("repos_url")
	val reposUrl: String?
) {
	fun toGitHubUser() =
		GitHubUser(
			id = id,
			login = login,
			avatarUrl = avatarUrl
		)
}