package com.ldileh.githubuser.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.ldileh.githubuser.data.local.entity.UserEntity
import com.ldileh.githubuser.databinding.ItemUserBinding
import com.ldileh.githubuser.ui.adapter.viewholder.UserViewHolder

class UserAdapter(
    private val listener: IUserAdapter
): PagingDataAdapter<UserEntity, UserViewHolder>(UserDiffCallback())  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            ItemUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, listener) }
    }

    class UserDiffCallback : DiffUtil.ItemCallback<UserEntity>() {
        override fun areItemsTheSame(oldItem: UserEntity, newItem: UserEntity) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: UserEntity, newItem: UserEntity) = oldItem == newItem
    }
}

interface IUserAdapter{

    fun onUserClicked(user: UserEntity)
}