package ttt.mardsoul.list_and_details.share.domain.entities

data class GitHubUser(
	val id: Int,
	val login: String,
	val avatarUrl: String?,
	var userDetails: GitHubUserDetails? = null
) {
	fun updateUserDetails(userDetails: GitHubUserDetails) {
		this.userDetails = userDetails
	}
}
