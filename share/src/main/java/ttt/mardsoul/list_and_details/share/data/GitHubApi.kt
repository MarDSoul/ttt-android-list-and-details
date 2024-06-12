package ttt.mardsoul.list_and_details.share.data

import retrofit2.http.GET
import retrofit2.http.Path
import ttt.mardsoul.list_and_details.share.data.dtoentities.GitHubRepoDto
import ttt.mardsoul.list_and_details.share.data.dtoentities.GitHubUserDetailsDto
import ttt.mardsoul.list_and_details.share.data.dtoentities.GitHubUserDto

interface GitHubApi {

	@GET("users")
	suspend fun getUsersList(): List<GitHubUserDto>

	@GET("user/{id}")
	suspend fun getUserDetails(@Path("id") userId: Int): GitHubUserDetailsDto

	@GET("users/{login}/repos")
	suspend fun getUserRepos(@Path("login") userLogin: String): List<GitHubRepoDto>

}