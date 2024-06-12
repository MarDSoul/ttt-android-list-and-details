package ttt.mardsoul.list_and_details_view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ttt.mardsoul.list_and_details.share.R
import ttt.mardsoul.list_and_details.share.domain.entities.GitHubUser
import ttt.mardsoul.list_and_details.share.ui.ListViewModel
import ttt.mardsoul.list_and_details.share.ui.ScreenListUiState
import ttt.mardsoul.list_and_details_view.databinding.FragmentGithublistBinding

@AndroidEntryPoint
class GitHubListFragment : Fragment(), ClickListener {

	private var _binding: FragmentGithublistBinding? = null
	private val binding get() = _binding!!

	private val viewModel: ListViewModel by viewModels()
	private val adapter = UserListRVAdapter(this as ClickListener)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		binding.userListRecyclerView.adapter = adapter
		viewLifecycleOwner.lifecycleScope.launch {
			repeatOnLifecycle(Lifecycle.State.CREATED) {
				viewModel.listUiState.collectLatest { uiState ->
					render(uiState)
				}
			}
		}
	}

	private fun render(uiState: ScreenListUiState) {
		when (uiState) {
			is ScreenListUiState.Error -> {
				binding.loadingLayout.root.visibility = View.GONE
				Snackbar.make(binding.root, uiState.message, Snackbar.LENGTH_INDEFINITE)
					.setAction(R.string.snackbar_action_label) {
						viewModel.loadGitHubUsersList()
					}
					.show()
			}

			ScreenListUiState.Loading -> {
				binding.loadingLayout.root.visibility = View.VISIBLE
			}

			is ScreenListUiState.SuccessList -> {
				binding.loadingLayout.root.visibility = View.GONE
				adapter.setGitHubUsersList(uiState.list)
			}
		}
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		_binding = FragmentGithublistBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}

	override fun onClickExpandedIcon(gitHubUser: GitHubUser) {
		viewLifecycleOwner.lifecycleScope.launch {
			val list = viewModel.loadUserRepos(gitHubUser)
			adapter.setReposList(gitHubUser, list)
		}
	}

	override fun onClickItem(gitHubUser: GitHubUser) {
		val action = GitHubListFragmentDirections
			.actionListFragmentToDetailsFragment(
				userId = gitHubUser.id,
				userLogin = gitHubUser.login,
				avatarUrl = gitHubUser.avatarUrl
			)
		findNavController().navigate(action)
	}
}

interface ClickListener {
	fun onClickExpandedIcon(gitHubUser: GitHubUser)
	fun onClickItem(gitHubUser: GitHubUser)
}