package ttt.mardsoul.list_and_details_compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import ttt.mardsoul.list_and_details.share.R
import ttt.mardsoul.list_and_details.share.domain.entities.GitHubUserDetails
import ttt.mardsoul.list_and_details.share.ui.DetailsUiState
import ttt.mardsoul.list_and_details.share.ui.DetailsViewModel

@Composable
fun DetailsScreen(
	modifier: Modifier = Modifier,
	snackbarHostState: SnackbarHostState,
	userId: Int,
	userLogin: String,
	avatarUrl: String?,
	viewModel: DetailsViewModel = hiltViewModel()
) {
	val uiState by viewModel.detailsUiState.collectAsState()

	LaunchedEffect(true) {
		viewModel.loadUserDetails(userId, userLogin)
	}

	Column(modifier = modifier) {
		Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
			SubcomposeAsyncImage(
				model = avatarUrl,
				contentDescription = null,
				contentScale = ContentScale.Crop,
				modifier = Modifier
					.padding(dimensionResource(R.dimen.small_pudding))
					.size(dimensionResource(R.dimen.avatar_detail_size)),
				loading = { CircularProgressIndicator() })
			Text(text = userLogin)
		}

		when (val state = uiState) {
			is DetailsUiState.Error -> {
				ErrorLayout(
					modifier = Modifier
						.weight(1f)
						.fillMaxSize(),
					snackbarMessage = state.message,
					snackbarHostState = snackbarHostState,
					performedAction = { viewModel.loadUserDetails(userId, userLogin) }
				)
			}

			DetailsUiState.Loading -> {
				LoadingLayout(
					modifier = Modifier
						.weight(1f)
						.fillMaxSize()
				)
			}

			is DetailsUiState.SuccessList -> {
				DetailsContent(
					modifier = Modifier
						.weight(1f)
						.fillMaxSize(), userDetails = state.userDetails
				)
			}
		}
	}
}

@Composable
fun DetailsContent(
	modifier: Modifier = Modifier,
	userDetails: GitHubUserDetails
) {
	Column(modifier = modifier) {
		Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
			Text(text = stringResource(R.string.details_name_label))
			Text(text = userDetails.name ?: stringResource(R.string.details_no_name))
		}
		Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
			Text(text = stringResource(R.string.details_created_at_label))
			Text(text = userDetails.createdAt)
		}
		Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
			Text(text = stringResource(R.string.details_location_label))
			Text(text = userDetails.location ?: stringResource(R.string.details_no_location))
		}
		userDetails.reposList?.let { reposList ->
			Text(text = stringResource(R.string.details_repos_label))
			LazyColumn(modifier = Modifier.testTag("repos_list")) {
				items(items = reposList, key = { it.id }) {
					Text(text = it.name)
				}
			}
		}

	}
}