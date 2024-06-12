package ttt.mardsoul.list_and_details.share.data.dtoentities


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ttt.mardsoul.list_and_details.share.domain.entities.GitHubRepo

@Serializable
data class GitHubRepoDto(
	@SerialName("id")
	val id: Int,
	@SerialName("name")
	val name: String,
	@SerialName("created_at")
	val createdAt: String,
	@SerialName("html_url")
	val htmlUrl: String?
) {
	fun toGitHubRepo() =
		GitHubRepo(
			id = id,
			name = name,
		)
}