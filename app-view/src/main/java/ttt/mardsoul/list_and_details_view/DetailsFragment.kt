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
import androidx.navigation.fragment.navArgs
import coil.load
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ttt.mardsoul.list_and_details.share.R
import ttt.mardsoul.list_and_details.share.ui.DetailsUiState
import ttt.mardsoul.list_and_details.share.ui.DetailsViewModel
import ttt.mardsoul.list_and_details_view.databinding.FragmentDetailsBinding

@AndroidEntryPoint
class DetailsFragment : Fragment() {

	private var _binding: FragmentDetailsBinding? = null
	private val binding get() = _binding!!

	private val viewModel: DetailsViewModel by viewModels()
	private val args: DetailsFragmentArgs by navArgs()

	private val adapter = ReposListRVAdapter()

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		initViews()
		viewModel.loadUserDetails(args.userId, args.userLogin)
		viewLifecycleOwner.lifecycleScope.launch {
			repeatOnLifecycle(Lifecycle.State.STARTED) {
				viewModel.detailsUiState.collectLatest { uiState ->
					render(uiState)
				}
			}
		}
	}

	private fun render(uiState: DetailsUiState) {
		when (uiState) {
			is DetailsUiState.Error -> {
				binding.loadingLayout.root.visibility = View.GONE
				Snackbar.make(binding.root, uiState.message, Snackbar.LENGTH_INDEFINITE)
					.setAction(R.string.snackbar_action_label) {
						viewModel.loadUserDetails(args.userId, args.userLogin)
					}
					.show()
			}

			DetailsUiState.Loading -> {
				binding.loadingLayout.root.visibility = View.VISIBLE
			}

			is DetailsUiState.SuccessList -> {
				binding.loadingLayout.root.visibility = View.GONE
				adapter.setReposList(uiState.userDetails.reposList)
				with(binding) {
					createdAtText.text = uiState.userDetails.createdAt
					locationText.text =
						uiState.userDetails.location ?: getString(R.string.details_no_location)
					nameText.text = uiState.userDetails.name ?: getString(R.string.details_no_name)
				}
			}
		}
	}

	private fun initViews() {
		with(binding) {
			detailsLoginText.text = args.userLogin
			detailsAvatarImage.load(args.avatarUrl)
			reposRecyclerView.adapter = adapter
		}
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		_binding = FragmentDetailsBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}