package ttt.mardsoul.list_and_details_view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ttt.mardsoul.list_and_details.share.domain.entities.GitHubRepo
import ttt.mardsoul.list_and_details_view.databinding.TextViewItemBinding

class ReposListRVAdapter() : RecyclerView.Adapter<ReposListRVAdapter.ReposListViewHolder>() {

	private var nameReposList = listOf<String>()

	fun setReposList(reposList: List<GitHubRepo>?) {
		reposList?.let { list ->
			nameReposList = list.map { it.name }
			notifyDataSetChanged()
		}
	}

	inner class ReposListViewHolder(
		private val binding: TextViewItemBinding
	) : RecyclerView.ViewHolder(binding.root) {

		fun bind(nameRepo: String) {
			binding.repoItemText.text = nameRepo
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReposListViewHolder {
		val layoutInflater = LayoutInflater.from(parent.context)
		val binding = TextViewItemBinding.inflate(layoutInflater, parent, false)
		return ReposListViewHolder(binding)
	}

	override fun getItemCount(): Int {
		return nameReposList.size
	}

	override fun onBindViewHolder(holder: ReposListViewHolder, position: Int) {
		holder.bind(nameReposList[position])
	}
}