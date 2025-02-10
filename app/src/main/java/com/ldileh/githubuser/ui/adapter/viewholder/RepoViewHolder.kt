package com.ldileh.githubuser.ui.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.ldileh.githubuser.data.local.entity.UserEntity
import com.ldileh.githubuser.databinding.ItemUserBinding
import com.ldileh.githubuser.ui.adapter.IUserAdapter
import com.ldileh.githubuser.utils.loadAvatar
import com.ldileh.githubuser.utils.safe

class RepoViewHolder(
    private val binding: ItemUserBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(data: UserEntity, listener: IUserAdapter){
        binding.apply {
            tvName.text = data.login.safe()
            ivAvatar.loadAvatar(data.avatarUrl.safe())

            root.setOnClickListener {
                listener.onUserClicked(data)
            }
        }
    }
}