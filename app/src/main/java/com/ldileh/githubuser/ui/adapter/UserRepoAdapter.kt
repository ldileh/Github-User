package com.ldileh.githubuser.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ldileh.githubuser.data.local.entity.RepoEntity
import com.ldileh.githubuser.databinding.ItemRepoBinding

class UserRepoAdapter :
    PagingDataAdapter<RepoEntity, UserRepoAdapter.RepoViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val binding = ItemRepoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val repo = getItem(position)
        repo?.let { holder.bind(it) }
    }

    class RepoViewHolder(private val binding: ItemRepoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(repo: RepoEntity) {
            binding.tvName.text = repo.name
            binding.tvDescription.text = repo.description ?: "No description"
            binding.tvStar.text = "⭐ ${repo.stars}"
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<RepoEntity>() {
            override fun areItemsTheSame(oldItem: RepoEntity, newItem: RepoEntity) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: RepoEntity, newItem: RepoEntity) = oldItem == newItem
        }
    }
}
