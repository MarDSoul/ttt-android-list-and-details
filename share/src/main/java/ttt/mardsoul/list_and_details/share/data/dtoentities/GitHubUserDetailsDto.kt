package ttt.mardsoul.list_and_details.share.data.dtoentities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ttt.mardsoul.list_and_details.share.domain.entities.GitHubUserDetails

@Serializable
data class GitHubUserDetailsDto(
	@SerialName("id")
	val id: Int,
	@SerialName("login")
	val login: String,
	@SerialName("avatar_url")
	val avatarUrl: String?,
	@SerialName("name")
	val name: String?,
	@SerialName("location")
	val location: String?,
	@SerialName("created_at")
	val createdAt: String,
	@SerialName("repos_url")
	val reposUrl: String?,
	@SerialName("public_repos")
	val publicRepos: Int?,
	@SerialName("blog")
	val blogUrl: String?
) {
	fun toGitHubUserDetails() =
		GitHubUserDetails(
			name = name,
			location = location,
			createdAt = createdAt
		)
}