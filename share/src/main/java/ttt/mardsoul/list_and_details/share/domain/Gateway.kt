package ttt.mardsoul.list_and_details.share.domain

import ttt.mardsoul.list_and_details.share.domain.entities.GitHubRepo
import ttt.mardsoul.list_and_details.share.domain.entities.GitHubUser
import ttt.mardsoul.list_and_details.share.domain.entities.GitHubUserDetails

interface Gateway {
	suspend fun getGitHubUsersList(): List<GitHubUser>
	suspend fun getUserDetails(userId: Int): GitHubUserDetails
	suspend fun getUserRepos(userLogin: String): List<GitHubRepo>
}