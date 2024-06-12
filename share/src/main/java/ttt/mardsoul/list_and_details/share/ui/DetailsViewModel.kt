package ttt.mardsoul.list_and_details.share.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ttt.mardsoul.list_and_details.share.domain.Gateway
import ttt.mardsoul.list_and_details.share.domain.entities.GitHubUserDetails
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
	private val gateway: Gateway
) : ViewModel() {
	private val _detailsUiState = MutableStateFlow<DetailsUiState>(DetailsUiState.Loading)
	val detailsUiState: StateFlow<DetailsUiState> = _detailsUiState.asStateFlow()

	fun loadUserDetails(userId: Int, userLogin: String) {
		viewModelScope.launch {
			try {
				_detailsUiState.value = DetailsUiState.Loading
				delay(100)
				val userDetails = gateway.getUserDetails(userId)
				val userRepos = gateway.getUserRepos(userLogin)
				userDetails.updateReposList(userRepos)
				_detailsUiState.value = DetailsUiState.SuccessList(userDetails)
			} catch (e: Exception) {
				_detailsUiState.value = DetailsUiState.Error()
			}
		}
	}
}

sealed interface DetailsUiState {
	object Loading : DetailsUiState
	data class SuccessList(val userDetails: GitHubUserDetails) : DetailsUiState
	data class Error(val message: String = TYPE_ERROR_MESSAGE) : DetailsUiState
}