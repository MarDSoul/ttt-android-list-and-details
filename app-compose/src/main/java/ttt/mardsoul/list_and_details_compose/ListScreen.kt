package ttt.mardsoul.list_and_details_compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import ttt.mardsoul.list_and_details.share.R
import ttt.mardsoul.list_and_details.share.domain.entities.GitHubRepo
import ttt.mardsoul.list_and_details.share.domain.entities.GitHubUser
import ttt.mardsoul.list_and_details.share.ui.ListViewModel
import ttt.mardsoul.list_and_details.share.ui.ScreenListUiState

@Composable
fun ListScreen(
	modifier: Modifier = Modifier,
	snackbarHostState: SnackbarHostState,
	viewModel: ListViewModel = hiltViewModel(),
	navigateToDetails: (GitHubUser) -> Unit
) {
	val uiState = viewModel.listUiState.collectAsState()

	when (val state = uiState.value) {
		is ScreenListUiState.Loading -> {
			LoadingLayout(modifier = modifier)
		}

		is ScreenListUiState.SuccessList -> {
			LazyColumn(modifier = modifier.testTag("users_list")) {
				items(items = state.list, key = { it.id }) {
					GitHubUserListItem(
						modifier = Modifier
							.fillMaxWidth()
							.clickable { navigateToDetails(it) }
							.testTag("user_item_${it.id}"),
						gitHubUser = it
					) { viewModel.loadUserRepos(it) }
				}
			}
		}

		is ScreenListUiState.Error -> {
			ErrorLayout(
				modifier = modifier,
				snackbarHostState = snackbarHostState,
				snackbarMessage = state.message,
				performedAction = { viewModel.loadGitHubUsersList() }
			)
		}
	}
}

@Composable
fun GitHubUserListItem(
	modifier: Modifier = Modifier,
	gitHubUser: GitHubUser,
	getUserRepos: suspend () -> List<GitHubRepo> = { emptyList() }
) {
	val reposList = remember { mutableStateOf<List<GitHubRepo>>(emptyList()) }

	Card(
		modifier = modifier
			.padding(dimensionResource(R.dimen.medium_pudding))
	) {
		var expanded by remember { mutableStateOf(false) }
		Column {
			Row(
				modifier = Modifier.fillMaxWidth(),
				verticalAlignment = Alignment.CenterVertically,
				horizontalArrangement = Arrangement.SpaceBetween
			) {
				SubcomposeAsyncImage(
					model = gitHubUser.avatarUrl,
					contentDescription = null,
					contentScale = ContentScale.Crop,
					modifier = Modifier
						.padding(dimensionResource(R.dimen.small_pudding))
						.size(dimensionResource(R.dimen.avatar_list_size))
						.clip(CircleShape),
					loading = { CircularProgressIndicator() }
				)
				Text(text = gitHubUser.login)
				IconButton(
					onClick = { expanded = !expanded },
					modifier = Modifier.testTag("expanded_button_${gitHubUser.id}")
				) {
					Icon(
						imageVector = ImageVector.vectorResource(
							id = if (expanded) R.drawable.expand_less_ic else R.drawable.expand_more_ic
						),
						contentDescription = stringResource(R.string.list_expanded_ic_cd)
					)
				}
			}
			if (expanded) {
				LaunchedEffect(true) {
					reposList.value = getUserRepos()
				}

				Column(modifier = Modifier.fillMaxWidth()) {
					Text(
						text = stringResource(R.string.list_expanded_title),
						modifier = Modifier.align(Alignment.CenterHorizontally)
					)
					Spacer(modifier = Modifier.size(dimensionResource(R.dimen.medium_pudding)))
					reposList.value.forEach {
						Text(
							text = it.name,
							modifier = Modifier
								.padding(start = dimensionResource(R.dimen.medium_pudding))
								.testTag("repo_item")
						)
					}
				}
			}
		}
	}
}