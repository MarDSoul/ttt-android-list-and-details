package ttt.mardsoul.list_and_details.share.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ttt.mardsoul.list_and_details.share.domain.Gateway
import ttt.mardsoul.list_and_details.share.domain.entities.GitHubRepo
import ttt.mardsoul.list_and_details.share.domain.entities.GitHubUser
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
	private val gateway: Gateway
) : ViewModel() {

	private val _listUiState = MutableStateFlow<ScreenListUiState>(ScreenListUiState.Loading)
	val listUiState: StateFlow<ScreenListUiState> = _listUiState.asStateFlow()

	init {
		loadGitHubUsersList()
	}

	fun loadGitHubUsersList() {
		viewModelScope.launch {
			try {
				_listUiState.value = ScreenListUiState.Loading
				delay(100)
				val usersList = gateway.getGitHubUsersList()
				_listUiState.value = ScreenListUiState.SuccessList(usersList)
			} catch (e: Exception) {
				_listUiState.value = ScreenListUiState.Error()
			}
		}
	}


	suspend fun loadUserRepos(user: GitHubUser): List<GitHubRepo> {
		return viewModelScope.async {
			return@async gateway.getUserRepos(user.login)
		}.await()
	}
}

sealed interface ScreenListUiState {
	object Loading : ScreenListUiState
	data class SuccessList(val list: List<GitHubUser>) : ScreenListUiState
	data class Error(val message: String = TYPE_ERROR_MESSAGE) : ScreenListUiState
}
