package ttt.mardsoul.list_and_details.share.domain.entities

data class GitHubUserDetails(
	val name: String? = null,
	val location: String? = null,
	val createdAt: String = "",
	var reposList: List<GitHubRepo>? = null
) {
	fun updateReposList(reposList: List<GitHubRepo>) {
		this.reposList = reposList
	}
}
