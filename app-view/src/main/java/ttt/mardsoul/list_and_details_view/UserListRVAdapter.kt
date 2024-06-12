package ttt.mardsoul.list_and_details_view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import ttt.mardsoul.list_and_details.share.domain.entities.GitHubRepo
import ttt.mardsoul.list_and_details.share.domain.entities.GitHubUser
import ttt.mardsoul.list_and_details_view.databinding.LayoutRvItemGithubuserBinding
import ttt.mardsoul.list_and_details_view.databinding.TextViewItemBinding

class UserListRVAdapter(
	private val clickListener: ClickListener
) : RecyclerView.Adapter<UserListRVAdapter.ViewHolder>() {

	private var gitHubUsersList = emptyList<UserUiItem>()

	fun setGitHubUsersList(data: List<GitHubUser>) {
		gitHubUsersList = data.map { UserUiItem(it) }
		notifyDataSetChanged()
	}

	fun setReposList(user: GitHubUser, reposList: List<GitHubRepo>) {
		val position = gitHubUsersList.indexOfFirst { it.user.id == user.id }
		gitHubUsersList[position].reposList = reposList
		notifyItemChanged(position)
	}

	private fun changeExpandedState(userUiItemId: Int) {
		val position = gitHubUsersList.indexOfFirst { it.user.id == userUiItemId }
		gitHubUsersList[position].isExpanded = !gitHubUsersList[position].isExpanded
		notifyItemChanged(position)
	}

	inner class ViewHolder(
		private val binding: LayoutRvItemGithubuserBinding
	) : RecyclerView.ViewHolder(binding.root) {

		fun bind(userUiItem: UserUiItem) {
			with(binding) {
				//For testing
				root.tag = "user_item_${userUiItem.user.id}"
				expandedDetailsIconButton.tag = "expanded_button_${userUiItem.user.id}"

				//Click listeners
				root.setOnClickListener { clickListener.onClickItem(userUiItem.user) }
				expandedDetailsIconButton.setOnClickListener {
					changeExpandedState(userUiItem.user.id)
					if (userUiItem.reposList.isEmpty()) {
						clickListener.onClickExpandedIcon(userUiItem.user)
					}
				}

				listItemAvatar.load(userUiItem.user.avatarUrl) {
					transformations(CircleCropTransformation())
				}
				listItemText.text = userUiItem.user.login

				//Expand repos list
				when (userUiItem.isExpanded) {
					true -> {
						expandedDetailsIconButton.setImageResource(ttt.mardsoul.list_and_details.share.R.drawable.expand_less_ic)
						val layoutInflater = LayoutInflater.from(reposLinearLayout.context)
						reposLinearLayout.removeAllViews()
						userUiItem.reposList.forEach {
							val itemBinding =
								TextViewItemBinding.inflate(layoutInflater)
							reposLinearLayout.addView(itemBinding.root.apply { text = it.name })
						}
						reposGroup.visibility = View.VISIBLE
					}

					false -> {
						expandedDetailsIconButton.setImageResource(ttt.mardsoul.list_and_details.share.R.drawable.expand_more_ic)
						reposGroup.visibility = View.GONE
					}
				}
			}
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val inflater = LayoutInflater.from(parent.context)
		val binding = LayoutRvItemGithubuserBinding.inflate(inflater, parent, false)
		return ViewHolder(binding)
	}

	override fun getItemCount(): Int {
		return gitHubUsersList.size
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		holder.bind(gitHubUsersList[position])
	}
}

data class UserUiItem(
	val user: GitHubUser,
	var isExpanded: Boolean = false,
	var reposList: List<GitHubRepo> = emptyList()
)