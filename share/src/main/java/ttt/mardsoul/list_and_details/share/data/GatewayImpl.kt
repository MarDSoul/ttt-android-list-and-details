package ttt.mardsoul.list_and_details.share.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext
import ttt.mardsoul.list_and_details.share.di.ApplicationScope
import ttt.mardsoul.list_and_details.share.di.IoDispatcher
import ttt.mardsoul.list_and_details.share.domain.Gateway
import ttt.mardsoul.list_and_details.share.domain.entities.GitHubRepo
import ttt.mardsoul.list_and_details.share.domain.entities.GitHubUser
import ttt.mardsoul.list_and_details.share.domain.entities.GitHubUserDetails
import javax.inject.Inject

class GatewayImpl @Inject constructor(
	private val gitHubApi: GitHubApi,
	@ApplicationScope private val coroutineScope: CoroutineScope,
	@IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : Gateway {

	override suspend fun getGitHubUsersList(): List<GitHubUser> =
		withContext(coroutineScope.coroutineContext + ioDispatcher) {
			gitHubApi.getUsersList().map { it.toGitHubUser() }
		}

	override suspend fun getUserDetails(userId: Int): GitHubUserDetails =
		withContext(coroutineScope.coroutineContext + ioDispatcher) {
			gitHubApi.getUserDetails(userId).toGitHubUserDetails()
		}

	override suspend fun getUserRepos(userLogin: String): List<GitHubRepo> =
		withContext(coroutineScope.coroutineContext + ioDispatcher) {
			gitHubApi.getUserRepos(userLogin).map { it.toGitHubRepo() }
		}

}